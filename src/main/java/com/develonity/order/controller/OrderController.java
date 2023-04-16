package com.develonity.order.controller;

import com.develonity.common.security.users.UserDetails;
import com.develonity.order.dto.OrderRequest;
import com.develonity.order.dto.OrderResponse;
import com.develonity.order.dto.PageDTO;
import com.develonity.order.service.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {

    private final OrderServiceImpl orderService;

    //기프트 카드 구매(주문하기)
    @PostMapping("/order")
    public Long orderGiftCard(@RequestBody OrderRequest orderRequest, @AuthenticationPrincipal UserDetails userDetails) {
        return orderService.order(orderRequest, userDetails.getUser().getId());
    }

    //유저 주문 내역 조회(페이징)
    @GetMapping("/user/orders")
    public Page<OrderResponse> getMyOrdersByPaging(@AuthenticationPrincipal UserDetails userDetails, @ModelAttribute PageDTO pageDTO) {
        return orderService.getMyOrdersByPaging(userDetails.getUser().getId(), pageDTO);
    }

    //유저 주문 상세 내역 조회
    @GetMapping("/user/orders/{orderId}")
    public OrderResponse getMyOrder(@PathVariable Long orderId , @AuthenticationPrincipal UserDetails userDetails) {
        return orderService.getMyOrder(orderId, userDetails.getUser().getId());
    }

}
