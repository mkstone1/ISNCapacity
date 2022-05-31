package com.example.isncapacity.Controller;

import com.example.isncapacity.Services.DiskService;
import com.example.isncapacity.Services.HypervisorService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

@Controller
public class DiskController {

    private HypervisorService hvService = new HypervisorService();
    private DiskService diskService = new DiskService();


    @GetMapping("/DiskToVM")
    public String linkDiskToVm(@RequestParam String name, @RequestParam String diskID,Model m) {
        int hypervisor = hvService.getHypervisorPlacement(name);

        m.addAttribute("vm", hvService.getAllHypervisors().get(hypervisor).getAllVmsOnHyperVisor());
        m.addAttribute("disk", hvService.getAllHypervisors().get(hypervisor).getDiskByID(diskID));
        m.addAttribute("hv", hvService.getAllHypervisors().get(hypervisor));

        return "linkDisk";
    }

    @GetMapping("/unbindDiskFromVM")
    public String unbindDiskFromVm(@RequestParam String diskID, @RequestParam String vmID){
        diskService.unbindDiskFromVm(diskID ,vmID);
        return "redirect:/VM?ID=" + vmID;
    }


    @PostMapping("/linkDisk")
    public String linkDisk(WebRequest dataFromForm, Model m){
        diskService.linkDiskToVM(dataFromForm.getParameter("vm"),dataFromForm.getParameter("disk"));
        m.addAttribute("hv", hvService.getAllHypervisors());
        return "redirect:/hypervisor?name="+ dataFromForm.getParameter("hv");
    }


}
