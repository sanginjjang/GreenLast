package com.example.greenlast.controllers.views.sangin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created on 2025-01-27 by 한상인
 */
@Controller
@RequestMapping("/view/mypage")
public class MyPageViewController_Sangin {
    @GetMapping("/")
    public String myPage() {
        return "/sangin/myPageAside";
    }

    @GetMapping("/infoForm")
    public String infoForm() {
        return "/sangin/myPage_infoForm";
    }
    @GetMapping("/infoModifyForm")
    public String infoModifyForm() {
        return "/sangin/myPage_infoModifyForm";
    }

    @GetMapping("/purchaseHistoryForm")
    public String purchaseHistoryForm() {
        return "/sangin/myPage_purchaseHistoryForm";
    }

    @GetMapping("/refundHistoryForm")
    public String refundHistoryForm() {
        return "/sangin/myPage_refundHistoryForm";
    }

    @GetMapping("/writtenBoardForm")
    public String writtenBoardForm() {
        return "/sangin/myPage_writtenBoardForm";
    }

    @GetMapping("/writtenCommentForm")
    public String writtenCommentForm() {
        return "/sangin/myPage_writtenCommentForm";
    }

    @GetMapping("/writtenReviewForm")
    public String writtenReviewForm() {
        return "/sangin/myPage_writtenReviewForm";
    }
}
