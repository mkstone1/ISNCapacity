package com.example.isncapacity.Controller;


import com.example.isncapacity.Services.DiskService;
import com.example.isncapacity.Services.VMService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.stereotype.Controller
public class VMController {
    VMService vmService = new VMService();
    DiskService diskService = new DiskService();


    @GetMapping("/VM")
    public String vmInfo(@RequestParam String ID, Model m){
        m.addAttribute("vm",vmService.getVmFromID(ID));
        m.addAttribute("disks", diskService.getDiskFromVMID(ID));

    return "VM";
    }
}
