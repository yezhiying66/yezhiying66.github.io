package com.wb.controller;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wb.pojo.InfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.servlet.ModelAndView;

@Controller
@CrossOrigin
@RestController
public class InfoController {

	//依赖注入
	@Autowired
	StringRedisTemplate redis;
	
	//图片上传
	@PostMapping(value="/upload")
	public Object upload(@RequestParam(name="photo")MultipartFile photo,
			@RequestParam(name="title")String title,HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			System.out.println("进入了upload");
			//封装属性
			InfoVO infoVO = new InfoVO();
			String addr = request.getRemoteAddr();
			infoVO.setAddr(addr);
			String no = UUID.randomUUID().toString();
			infoVO.setNo(no);
			String pattern = "yyyy/MM/dd HH:mm:ss";
			String now = new SimpleDateFormat(pattern).format(System.currentTimeMillis());
			infoVO.setNow(now);
			infoVO.setTitle(title);
			System.out.println("封装属性:"+infoVO);
			//序列化  对象->字符串
			String value= JSONObject.toJSONString(infoVO);
			System.out.println("对象->字符串:"+value);
			//存储到List -> 新的图片信息放置在前面
			String key = "huali:soft:infos";
			redis.opsForList().leftPush(key, value);
			
			//处理图片
			key = "huali:soft:photos";
			//encode 转码  byte[] -> String
			value = Base64Utils.encodeToString(photo.getBytes());
			System.out.println("encode 转码  byte[] -> String:"+value);
			//存储到Hash
			redis.opsForHash().put(key, no, value);
			
			result.put("code", 200);
			result.put("message", "上传成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", 500);
			result.put("message", "上传失败");
		}
		return result;
	}

	@GetMapping(value = "/list")
	public ModelAndView listindex(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/linshi.html");
		return mv;
	}

	//列表显示
	@ResponseBody
	@GetMapping(value="/list/{last}/{next}")
	public Object list(@PathVariable(name="last")int last,@PathVariable(name="next")int next) {
		System.out.println("list");
		//缓存List中的数据获取 反序列化为对象 添加到集合中
		List<InfoVO> infos = new ArrayList<InfoVO>();
		String key = "huali:soft:infos";
		long count = redis.opsForList().size(key);
		for(String value:redis.opsForList().range(key, last, next)) {
			InfoVO info = JSONObject.parseObject(value, InfoVO.class);
			infos.add(info);
		}
		System.out.println("//缓存List中的数据获取 反序列化为对象 添加到集合中"+infos);
		return infos;
	}
	
	//删除
	@PostMapping(value="/delel")
	public Object delel(@RequestParam(name="title")String title,@RequestParam(name="addr")String addr,
			@RequestParam(name="now")String now,@RequestParam(name="no")String no,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		//封装属性
		String key = "huali:soft:infos";
		InfoVO infoVO = new InfoVO();
		infoVO.setAddr(addr);
		infoVO.setNo(no);
		infoVO.setNow(now);
		infoVO.setTitle(title);
		System.out.println("封装属性:"+infoVO);
		//序列化  对象->字符串
		String value= JSONObject.toJSONString(infoVO);
		System.out.println("对象->字符串:"+value);
		try {
			redis.opsForList().remove(key, 0, value);
			redis.opsForHash().delete("huali:soft:photos", no);
			result.put("code", 200);
			result.put("message", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", 500);
			result.put("message", "删除失败");
		}
		return result;
	}
	
	//图片显示
	@GetMapping(value="/show/{no}")
	public void show(@PathVariable(name="no")String no,HttpServletResponse response) {
		try {
			//缓存Hash获取数据 解码为二进制 并写入到输出流中
			String key = "huali:soft:photos";
			String value = redis.opsForHash().get(key, no).toString();
			//decode 解码   String -> byte[]
			byte[] b = Base64Utils.decodeFromString(value);
			//输出流
			OutputStream out = response.getOutputStream();
			out.write(b);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}
