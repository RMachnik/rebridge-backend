package application.rest.model;

import domain.Comment;
import domain.InspirationDetail;
import dto.CommentDto;
import dto.InspirationDetailDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InspirationMapper {

    InspirationMapper INSTANCE = Mappers.getMapper(InspirationMapper.class);

    InspirationDetail fromDtoToInspiration(InspirationDetailDto inspirationDetailDto);

    Comment fromDtoToComment(CommentDto commentDto);
}
