package com.eatda.domain.menu.controller;

import com.eatda.domain.menu.dto.MenuDTO;
import com.eatda.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menu")
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/{menuId}")
    public ResponseEntity<MenuDTO> getMenuById(@PathVariable Long menuId) {
        return ResponseEntity.ok(menuService.getMenuById(menuId));
    }

    @PostMapping("/create/{presidentId}")
    public ResponseEntity<MenuDTO> createMenu(@RequestBody MenuDTO menuDTO, @PathVariable Long presidentId) {
        return new ResponseEntity<>(menuService.createMenu(menuDTO, presidentId), HttpStatus.CREATED);
    }

    @PutMapping("/{menuId}/update")
    public ResponseEntity<MenuDTO> updateMenu(@PathVariable Long menuId, @RequestBody MenuDTO menuDTO) {
        menuDTO.setId(menuId);
        return ResponseEntity.ok(menuService.updateMenu(menuDTO));
    }

    @DeleteMapping("/{menuId}")
    public ResponseEntity<String> deleteMenu(@PathVariable Long menuId) {
        menuService.deleteMenu(menuId);
        return ResponseEntity.ok("삭제 완료");
    }
}
