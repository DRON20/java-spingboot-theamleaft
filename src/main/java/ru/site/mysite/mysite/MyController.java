package ru.site.mysite.mysite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.site.mysite.mysite.Entitys.MtsDB;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.List;

@Controller
public class MyController {

    @Autowired
    private MtsRepository mtsRepository;

    @GetMapping(value = "/")
    public String hello(Model model){
        return "user/home";
    }

    @GetMapping("/mts")
    public String mts(Model model){
        model.addAttribute("images", mtsRepository.findAll());
        return "user/mts";
    }

    @PostMapping("/mts")
    public String mtsResult(Model model, @RequestParam("file") MultipartFile file){
        if (file.isEmpty()){
            return "redirect:/mts";
        }
        String answer = null;
        String s = null;
        try{
            BASE64Encoder encoder = new BASE64Encoder();
            String imgString = encoder.encode(file.getBytes());
            Service service = new Service("http://localhost:8001", file.getBytes());
            answer = service.requestToService();
            s = answer.replaceAll("\\(|\\)|\\'|\\,",""); //FIXME
            model.addAttribute("img", imgString); //FIXME
            model.addAttribute("imgClass", s.split(" ")[0]);
            model.addAttribute("prob", s.split(" ")[1]);
            return "user/result";
        } catch (Exception e) {
            e.printStackTrace();
            return "error/404";
        }
    }

    @PostMapping("/mtsdemo")
    public String mtsResult2(Model model, @ModelAttribute("id") Long id){
        String answer = null;
        String s = null;
        try{
            Service service = new Service("http://localhost:8001", new BASE64Decoder().decodeBuffer(mtsRepository.findOne(id).img));
            answer = service.requestToService();
            s = answer.replaceAll("\\(|\\)|\\'|\\,","");
            model.addAttribute("img", mtsRepository.findOne(id).img);
            model.addAttribute("imgClass", s.split(" ")[0]);
            model.addAttribute("prob", s.split(" ")[1]);
            return "user/result";
        } catch (Exception e) {
            e.printStackTrace();
            return "error/404";
        }
    }

    @GetMapping(value = "/imagecar")
    public String imagecar(Model model){
        return "user/imagecar";
    }

    @GetMapping(value = "/imagesing")
    public String imagesing(Model model){
        return "user/imagesing";
    }

    @GetMapping(value = "/wagonfill")
    public String wagonfill(Model model){
        return "user/wagonfill";
    }

    @GetMapping(value = "/metroqueue")
    public String metroqueue(Model model){
        return "user/metroqueue";
    }

    @GetMapping("/login")
    public String login(Model model){
        return "login";
    }

    @GetMapping("/admin")
    public String admin(Model model){
        return "admin/home";
    }

    @GetMapping("/admin/mts")
    public String adminMts(Model model){
        List<MtsDB> data = mtsRepository.findAll();
        model.addAttribute("list", data);
        return "admin/mts";
    }

    @GetMapping("/admin/imagecar")
    public String adminCar(Model model){
        return "admin/imagecar";
    }

    @GetMapping("/admin/imagesing")
    public String adminSing(Model model){
        return "admin/imagesing";
    }

    @GetMapping("/admin/wagonfill")
    public String adminWagon(Model model){
        return "admin/wagonfill";
    }

    @GetMapping("/admin/metroqueue")
    public String adminMetro(Model model){
        return "admin/metroqueue";
    }

    @PostMapping("/admin/mts")
    public String adminAdd(Model model, @RequestParam("file") MultipartFile file){
        try {
            String name = file.getOriginalFilename();
            String img = new BASE64Encoder().encode(file.getBytes());
            mtsRepository.save(new MtsDB(name, img));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/admin/mts";
    }

    @PostMapping("/admin/mts/del")
    public String adminDel(Model model, @ModelAttribute("id") Long id){
        try {
            mtsRepository.delete(Long.valueOf(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/mts";
    }

}
