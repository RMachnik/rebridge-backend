package application.service;

import application.dto.CreateCategoryDto;
import application.dto.CreateItemDto;
import application.dto.CreateRoomDto;
import application.dto.CurrentUser;
import domain.catalogue.Catalogue;
import domain.catalogue.Category;
import domain.catalogue.Item;
import domain.catalogue.Room;
import domain.project.Project;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CatalogueService {

    ProjectService projectService;

    public Catalogue get(CurrentUser currentUser, String projectId) {
        return projectService.findByUserIdAndProjectId(currentUser.getId(), projectId).getCatalogue();
    }

    public Room addRoom(CurrentUser currentUser, String projectId, CreateRoomDto createRoomDto) {
        Room room = Room.create(createRoomDto.getName());
        Project project = projectService.findByUserIdAndProjectId(currentUser.getId(), projectId);
        project.getCatalogue().addRoom(room);
        projectService.save(project);
        return room;
    }

    public Category addCategory(CurrentUser currentUser, String projectId, String roomId, CreateCategoryDto createCategoryDto) {
        Project project = projectService.findByUserIdAndProjectId(currentUser.getId(), projectId);
        Room room = project.getCatalogue().findRoom(roomId);
        Category category = Category.create(createCategoryDto.getName());
        room.addCategory(category);
        projectService.save(project);
        return category;
    }

    public Item addItem(CurrentUser currentUser, String projectId, String roomId, String categoryId, CreateItemDto createItemDto) {
        Project project = projectService.findByUserIdAndProjectId(currentUser.getId(), projectId);
        Room room = project.getCatalogue().findRoom(roomId);
        Category category = room.findCategory(categoryId);
        Item item = Item.create(
                createItemDto.getDescription(),
                createItemDto.getSizing(),
                createItemDto.getQuantity(),
                createItemDto.getPrize(),
                createItemDto.getAdditionalInfo()
        );
        category.addItem(item);
        projectService.save(project);
        return item;

    }

    public void removeRoom(CurrentUser currentUser, String projectId, String roomId) {
        Project project = projectService.findByUserIdAndProjectId(currentUser.getId(), projectId);
        project.getCatalogue().removeRoom(roomId);
        projectService.save(project);
    }

    public void removeCategory(CurrentUser currentUser, String projectId, String roomId, String categoryId) {
        Project project = projectService.findByUserIdAndProjectId(currentUser.getId(), projectId);
        Room room = project.getCatalogue().findRoom(roomId);
        room.removeCategory(categoryId);
        projectService.save(project);
    }

    public void removeItem(CurrentUser currentUser, String projectId, String roomId, String categoryId, String itemId) {
        Project project = projectService.findByUserIdAndProjectId(currentUser.getId(), projectId);
        Room room = project.getCatalogue().findRoom(roomId);
        Category category = room.findCategory(categoryId);
        category.removeItem(itemId);
        projectService.save(project);
    }
}
