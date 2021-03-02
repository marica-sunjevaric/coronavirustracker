package com.javabranis.coronavirustracker.controllers;

import com.javabranis.coronavirustracker.models.LocationStats;
import com.javabranis.coronavirustracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.DecimalFormat;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/")
    public String home(Model model) {
        List<LocationStats> allStats = coronaVirusDataService.getAllStats();
        int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
        int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", formatNumbers(totalReportedCases));
        model.addAttribute("totalNewCases", formatNumbers(totalNewCases));
        return "home";
    }
    public String formatNumbers (int value) {
        DecimalFormat df = new DecimalFormat("###,###,###");
        return df.format(value);
    }
}
