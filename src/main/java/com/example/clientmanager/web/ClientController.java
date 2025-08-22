package com.example.clientmanager.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.clientmanager.domain.Client;
import com.example.clientmanager.repo.ClientRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/clients")
public class ClientController {

    private final ClientRepository repo;

    public ClientController(ClientRepository repo) {
        this.repo = repo;
    }

    // 一覧
    @GetMapping
    public String list(Model model) {
        model.addAttribute("clients", repo.findAll());
        return "clients/list";
    }

    // 新規フォーム
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("client", new Client());
        return "clients/form";
    }

    // 作成
    @PostMapping
    public String create(@Valid @ModelAttribute("client") Client client,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "clients/form";
        repo.save(client);
        return "redirect:/clients";
    }

    // 編集フォーム
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        var c = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("invalid id"));
        model.addAttribute("client", c);
        return "clients/form";
    }

    // 更新
    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
            @Valid @ModelAttribute("client") Client client,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "clients/form";
        client.setId(id);
        repo.save(client);
        return "redirect:/clients";
    }

    // 削除
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/clients";
    }
}
