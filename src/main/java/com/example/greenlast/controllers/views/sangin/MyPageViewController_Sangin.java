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
        System.out.println("/view/myPage...");
        return "/sangin/myPageAside";
    }

    @GetMapping("/infoForm")
    public String infoForm() {
        System.out.println("/view/myPage/infoForm...");
        return "/sangin/myPage_infoForm";
    }

    @GetMapping("/purchaseHistoryForm")
    public String purchaseHistoryForm() {
        System.out.println("/view/myPage/purchaseHistoryForm...");
        return "/sangin/myPage_purchaseHistoryForm";
    }

    @GetMapping("/refundHistoryForm")
    public String refundHistoryForm() {
        System.out.println("/view/myPage/refundHistoryForm...");
        return "/sangin/myPage_refundHistoryForm";
    }

    @GetMapping("/writtenBoardForm")
    public String writtenBoardForm() {
        System.out.println("/view/myPage/writtenBoardForm...");
        return "/sangin/myPage_writtenBoardForm";
    }

    @GetMapping("/writtenCommentForm")
    public String writtenCommentForm() {
        System.out.println("/view/myPage/writtenCommentForm...");
        return "/sangin/myPage_writtenCommentForm";
    }

    @GetMapping("/writtenReviewForm")
    public String writtenReviewForm() {
        System.out.println("/view/myPage/writtenReviewForm...");
        return "/sangin/myPage_writtenReviewForm";
    }
}
