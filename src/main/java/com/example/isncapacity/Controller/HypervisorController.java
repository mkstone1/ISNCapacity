package com.example.isncapacity.Controller;


import com.example.isncapacity.Services.HypervisorService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

@org.springframework.stereotype.Controller
public class HypervisorController {
    private HypervisorService hvService = new HypervisorService();

    @GetMapping("/hypervisor")
    public String getVmOnHypervisor(@RequestParam String name, Model m) {
        int hypervisor = hvService.getHypervisorPlacement(name);

        m.addAttribute("hv", hvService.getAllHypervisors().get(hypervisor));
        m.addAttribute("vm", hvService.getAllHypervisors().get(hypervisor).getAllVmsOnHyperVisor());
        m.addAttribute("disk", hvService.getAllHypervisors().get(hypervisor).getUnassignedDisks());

        return "hypervisor";
    }

    @GetMapping("/AllHypervisors")
    public String allHypervisors(Model m){
        m.addAttribute("hv", hvService.getAllHypervisors());
        return "allHypervisors";
    }

    @GetMapping("/CreateHypervisor")
    public String createHypervisor(){

        return "createHypervisor";
    }

    @PostMapping("/CreateHypervisor")
    public String createHypervisor(WebRequest hypervisorData){
        String hypervisorName = hypervisorData.getParameter("hypervisorName");
        String hypervisorRAM = hypervisorData.getParameter("RAM");
        String storageSpace = hypervisorData.getParameter("storageSpace");

        hvService.createHypervisor(hypervisorName,hypervisorRAM,storageSpace);

        return "index";
    }
}
