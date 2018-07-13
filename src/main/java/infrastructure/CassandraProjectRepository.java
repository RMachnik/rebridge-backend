package infrastructure;

import application.service.RepositoryExceptions;
import domain.*;
import infrastructure.entity.CommentEntity;
import infrastructure.entity.InspirationDetailEntity;
import infrastructure.entity.InspirationEntity;
import infrastructure.entity.ProjectEntity;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@AllArgsConstructor
public class CassandraProjectRepository implements ProjectRepository {

    ProjectCrudRepository projectCrudRepository;
    InspirationCrudRepository inspirationCrudRepository;
    CommentCrudRepository commentCrudRepository;


    @Override
    public Optional<Project> findById(UUID id) {
        ProjectEntity projectEntity = projectCrudRepository.findById(id)
                .orElseThrow(() -> new RepositoryExceptions.ProjectRepositoryException(format("unable to load project %s", id)));

        Iterable<InspirationEntity> inspirationEntities = projectEntity.loadInspirations(inspirationCrudRepository);
        loadInspirations(inspirationEntities);

        return Optional.empty();
    }

    private List<Inspiration> loadInspirations(Iterable<InspirationEntity> inspirationEntities) {
        StreamSupport.stream(inspirationEntities.spliterator(), false)
                .map(entity ->
                        Inspiration.builder()
                                .id(entity.getId())
                                .name(entity.getName())
                                .inspirationDetail(getInspirationDetail(entity.getInspirationDetail()))
                );
        return null;
    }

    private InspirationDetail getInspirationDetail(InspirationDetailEntity inspirationDetailEntity) {
        Iterable<CommentEntity> commentEntities = inspirationDetailEntity.loadComments(commentCrudRepository);
        List<Comment> comments = getComments(commentEntities);
        return InspirationDetail.builder()
                .description(inspirationDetailEntity.getDescription())
                .picture(inspirationDetailEntity.getPicture())
                .rating(inspirationDetailEntity.getRating())
                .url(inspirationDetailEntity.getUrl())
                .comments(comments)
                .build();
    }

    private List<Comment> getComments(Iterable<CommentEntity> commentEntities) {
        return StreamSupport.stream(commentEntities.spliterator(), false)
                .map(entity -> Comment.builder()
                        .id(entity.getId())
                        .author(entity.getAuthor())
                        .userId(entity.getUserId())
                        .content(entity.getContent())
                        .date(entity.getDate())
                        .build()
                )
                .collect(toList());
    }

    @Override
    public Try<Project> save(Project entity) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}
