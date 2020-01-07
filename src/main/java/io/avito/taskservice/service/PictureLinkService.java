package io.avito.taskservice.service;

import io.avito.taskservice.entity.PictureLink;
import io.avito.taskservice.entity.Task;
import io.avito.taskservice.repository.PictureLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PictureLinkService {

    private PictureLinkRepository pictureLinkRepository;

    @Autowired
    public void setPictureLinkRepository(PictureLinkRepository pictureLinkRepository) {
        this.pictureLinkRepository = pictureLinkRepository;
    }

    public List<String> getAllPictureLinksByTask(Task task) {
        List<PictureLink> pictureLinks = pictureLinkRepository.findAllByTask(task);

        return pictureLinks.stream()
                .map(PictureLink::getLink)
                .collect(Collectors.toList());
    }

    public PictureLink savePictureLink(PictureLink pictureLink) {
        return pictureLinkRepository.save(pictureLink);
    }

    public void deletePictureLinksByTask(Task task) {
        List<PictureLink> pictureLinks = pictureLinkRepository.findAllByTask(task);
        pictureLinkRepository.deleteAll(pictureLinks);
    }
}
