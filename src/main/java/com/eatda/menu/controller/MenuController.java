package com.eatda.menu.controller;

import com.eatda.menu.domain.Menu;
import com.eatda.menu.domain.MenuDTO;
import com.eatda.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @PostMapping("/create")
    public ResponseEntity<MenuDTO> createMenu(@RequestBody MenuDTO menuDTO){
        MenuDTO createMenu = menuService.createMenu(menuDTO);
        if(createMenu != null){
            return ResponseEntity.ok(createMenu);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{menuId}/update")
    public ResponseEntity<MenuDTO> updateMenu(@PathVariable Long menuId, @RequestBody MenuDTO menuDTO){
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
        try {
            menuService.deleteMenu(menuId);
            return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
