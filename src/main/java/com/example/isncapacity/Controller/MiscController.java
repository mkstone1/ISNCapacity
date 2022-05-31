package com.example.isncapacity.Controller;

import com.example.isncapacity.Services.UpdateDataService;
import com.example.isncapacity.Services.HypervisorService;;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;



@org.springframework.stereotype.Controller
public class MiscController {
    HypervisorService hvService = new HypervisorService();
    UpdateDataService updateDataService = new UpdateDataService();



    @GetMapping("/")
    public String getFrontPage(Model m) {

        return "index";
    }

    @GetMapping("/updatedata")
    public String updateData(){
        return "updateData";
    }

    @PostMapping("/updateData")
    public String updateData(Model m){
        updateDataService.updateData();
        m.addAttribute("hv", hvService.getAllHypervisors());
        return "redirect:/";
    }










}
