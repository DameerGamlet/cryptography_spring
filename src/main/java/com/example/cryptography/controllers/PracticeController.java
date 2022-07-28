package com.example.cryptography.controllers;

import com.example.cryptography.content.caesar.CaesarCipher;
import com.example.cryptography.domain.Caesar;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/practice")
public class PracticeController {

    @GetMapping("/caesar")
    public String text() {
        return "caesar!!!";
    }

    @PostMapping("/caesar")
    public String cipherCaesar(@RequestBody Caesar caesar) {
        System.out.println("Зашифровать текст");
        System.out.println("\n" + caesar);
        String result = new CaesarCipher().encrypt(caesar.getText(), caesar.getKey());
        System.out.println("xxxxx");
        System.out.println(result);
        return result;
    }

    @PostMapping("/decaesar")
    public String decipherCaesar(@RequestBody Caesar caesar) {
        System.out.println("Расшифровать текст");
        System.out.println("\n" + caesar);
        String result = new CaesarCipher().decrypt(caesar.getText(), caesar.getKey());
        System.out.println("xxxxx");
        System.out.println(result);
        return result;
    }
}
