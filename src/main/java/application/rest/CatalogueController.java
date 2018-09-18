package application.rest;

import application.dto.*;
import application.service.CatalogueService;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(
        path = "/projects/{projectId}/catalogue",
        consumes = APPLICATION_JSON_UTF8_VALUE,
        produces = APPLICATION_JSON_UTF8_VALUE
)
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class CatalogueController {

    CatalogueService catalogueService;

    @GetMapping
    ResponseEntity<CatalogueDto> get(@AuthenticationPrincipal CurrentUser currentUser, @PathVariable String projectId) {
        return ResponseEntity.ok(CatalogueDto.convert(catalogueService.get(currentUser, projectId)));
    }

    @PostMapping("/{catalogueId}/rooms")
    ResponseEntity<RoomDto> addRoom(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable String projectId,
            @RequestBody CreateRoomDto createRoomDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(RoomDto.convert(catalogueService.addRoom(currentUser, projectId, createRoomDto)));
    }

    @DeleteMapping("/{catalogueId}/rooms/{roomId}")
    ResponseEntity deleteRoom(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable String projectId,
            @PathVariable String roomId
    ) {
        catalogueService.removeRoom(currentUser, projectId, roomId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{catalogueId}/rooms/{roomId}/categories")
    ResponseEntity<CategoryDto> addCategory(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable String projectId,
            @PathVariable String roomId,
            @RequestBody CreateCategoryDto createCategoryDto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CategoryDto.convert(
                        catalogueService.addCategory(currentUser, projectId, roomId, createCategoryDto))
                );
    }

    @DeleteMapping("/{catalogueId}/rooms/{roomId}/categories/{categoryId}")
    ResponseEntity<CategoryDto> addCategory(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable String projectId,
            @PathVariable String roomId,
            @PathVariable String categoryId
    ) {
        catalogueService.removeCategory(currentUser, projectId, roomId, categoryId);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping("/{catalogueId}/rooms/{roomId}/categories/{categoryId}/items")
    ResponseEntity<ItemDto> addItem(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable String projectId,
            @PathVariable String roomId,
            @PathVariable String categoryId,
            @RequestBody CreateItemDto createItemDto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ItemDto.convert(catalogueService.addItem(currentUser, projectId, roomId, categoryId, createItemDto))
                );
    }

    @PostMapping("/{catalogueId}/rooms/{roomId}/categories/{categoryId}/items/{itemId}")
    ResponseEntity<ItemDto> addItem(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable String projectId,
            @PathVariable String roomId,
            @PathVariable String categoryId,
            @PathVariable String itemId
    ) {
        catalogueService.removeItem(currentUser, projectId, roomId, categoryId, itemId);
        return ResponseEntity
                .noContent()
                .build();
    }

}
