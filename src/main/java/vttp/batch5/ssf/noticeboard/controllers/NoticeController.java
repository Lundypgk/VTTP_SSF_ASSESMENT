

package vttp.batch5.ssf.noticeboard.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.json.JsonObject;
import jakarta.validation.*;
import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.services.NoticeService;

@Controller
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/")
    public String showNoticeForm(Model model) {
        model.addAttribute("notice", new Notice());
        return "notice";
    }

    @PostMapping("/notice")
    public String submitNotice(@Valid Notice notice, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("notice", notice);
            return "notice";
        }
        JsonObject Success = noticeService.postToNoticeServer(notice);
        if (Success.containsKey("id")) {
            model.addAttribute("id", Success.getString("id"));
            return "view2";
        } else {
            model.addAttribute("message", Success.getString("message"));
            return "view3";
        }
    }

    // redis-cli RANDOMKEY
    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        if (noticeService.helth()) {
            return ResponseEntity.ok("{\"status\": \"healthy\"}");
        } else {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                                .body("{\"status\": \"unhealthy\"}");
        }
    }

}
