package com.wayapay.payment.controller;


import com.wayapay.payment.dto.TransactionDTO;
import com.wayapay.payment.dto.TransactionResponseDTO;
import com.wayapay.payment.service.TransactionService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/transaction")
@CrossOrigin(origins = "*")
@Api(value = "Transaction", description = "Transaction")
public class TransactionController {

    TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @ResponseBody
    @PostMapping(value = "/create", produces = "application/json")
    public ResponseEntity<TransactionResponseDTO> saveOrder(@Valid @RequestBody TransactionDTO transactionDTO) {
        TransactionResponseDTO transactionResponseDTO = transactionService.create(transactionDTO);
        return new ResponseEntity<>(transactionResponseDTO, HttpStatus.OK);
    }


}
