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
        MenuDTO menuDTO = menuService.getMenuById(menuId);
        if (menuDTO != null) {
            return ResponseEntity.ok(menuDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create/{presidentId}")
    public ResponseEntity<MenuDTO> createMenu(@RequestBody MenuDTO menuDTO, @PathVariable Long presidentId) {
        MenuDTO createdMenu = menuService.createMenu(menuDTO, presidentId);

        if (createdMenu != null) {
            return new ResponseEntity<>(createdMenu, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{menuId}/update")
    public ResponseEntity<MenuDTO> updateMenu(@PathVariable Long menuId, @RequestBody MenuDTO menuDTO) {
        menuDTO.setId(menuId);
        MenuDTO updatedMenu = menuService.updateMenu(menuDTO);
        if (updatedMenu != null) {
            return ResponseEntity.ok(updatedMenu);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{menuId}")
    public ResponseEntity<String> deleteMenu(@PathVariable Long menuId) {
        menuService.deleteMenu(menuId);
        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
    }
}
