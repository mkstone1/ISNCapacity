package com.example.isncapacity.Controller;

import com.example.isncapacity.Services.CustomerServices;
import com.example.isncapacity.Services.HypervisorService;
import com.example.isncapacity.Services.VMService;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

@org.springframework.stereotype.Controller
public class CustomerController {
    CustomerServices customerServices = new CustomerServices();
    HypervisorService hvService = new HypervisorService();
    VMService vmService = new VMService();

    @GetMapping("/CreateCustomer")
    public String getVmOnHypervisor(@RequestParam String error, Model m) {
        if (!error.equals("false")) {
            error = "Invalid name or abbreviation";
            m.addAttribute("er", error);
        }
        return "createCustomer";
    }

    @GetMapping("/AllCustomers")
    public String allCustomers(Model m) {
        m.addAttribute("cust", customerServices.getAllCustomers());

        return "allCustomers";
    }

    @GetMapping("/Customer")
    public String customer(@RequestParam String name, Model m) {
        m.addAttribute("customer",customerServices.getCustomerFromName(name));
        m.addAttribute("vm", vmService.getVmsFromCustomer(customerServices.getCustomerFromName(name)));
        return "customer";
    }

    @GetMapping("/ChooseHypervisorForAdd")
    public String chooseHv(@RequestParam String name, Model m) {
        m.addAttribute("customer",customerServices.getCustomerFromName(name));
        m.addAttribute("hv", hvService.getAllHypervisors());
        return "chooseHV";
    }

    @GetMapping("/LinkVmToCustomer")
    public String addVmToCustomer(@RequestParam String hv,@RequestParam String customer ,Model m) {
        int hypervisor = hvService.getHypervisorPlacement(hv);
        String hypervisorID = hvService.getAllHypervisors().get(hypervisor).getHypervisorID();
        m.addAttribute("hv", hvService.getAllHypervisors().get(hypervisor));
        m.addAttribute("vm",vmService.getVMsFromHypervisorWithNoCustomerAssigned(hypervisorID));
        m.addAttribute("customer", customer);

        return "chooseVM";
    }

    @PostMapping("LinkVmToCustomer")
    public String addVmToCustomer(WebRequest dataFromForm){
        String vmName = dataFromForm.getParameter("vm");
        String hv = dataFromForm.getParameter("hv");
        String customerName = dataFromForm.getParameter("customer");
        vmService.linkVmToCustomer(vmName, customerName);


        return "redirect:/";
    }


    @PostMapping("/createCustomer")
    public String createCustomer(WebRequest customerData) {
        String customerName = customerData.getParameter("customerName");
        String customerAbbreviation = customerData.getParameter("abbreviation");
        boolean goodData = customerServices.createCustomer(customerName, customerAbbreviation);
        if (goodData == false) {
            return "redirect:/CreateCustomer?error=true";
        } else {
            return "redirect:/AllCustomers";
        }
    }
    @GetMapping("/RemoveVmFromCustomer")
    public String removeVmFromCust(@RequestParam String vmID, @RequestParam String name){
        vmService.removeVmFromCustomer(vmID);
        return "redirect:/Customer?name=" + name;
    }
}
