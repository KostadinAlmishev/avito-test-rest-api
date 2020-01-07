package io.avito.taskservice.repository;

import io.avito.taskservice.entity.PictureLink;
import io.avito.taskservice.entity.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PictureLinkRepository extends CrudRepository<PictureLink, Long> {

    List<PictureLink> findAllByTask(Task task);

}
