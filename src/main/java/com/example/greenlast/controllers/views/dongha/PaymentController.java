package com.example.greenlast.controllers.views.dongha;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * packageName    : com.example.greenlast.controllers.views.dongha
 * fileName       : PaymentController
 * author         : 이동하
 * date           : 25. 2. 6.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 6.        이동하       최초 생성
 */
@Controller
@RequestMapping("/payment")
public class PaymentController {

    @GetMapping("/history")
    public String paymentHistory() {
        return "dongha/paymentHistory";
    }
}
