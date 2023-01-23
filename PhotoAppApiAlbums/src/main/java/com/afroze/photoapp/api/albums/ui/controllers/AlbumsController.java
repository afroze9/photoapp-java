package com.afroze.photoapp.api.albums.ui.controllers;

import com.afroze.photoapp.api.albums.data.AlbumEntity;
import com.afroze.photoapp.api.albums.service.AlbumsService;
import com.afroze.photoapp.api.albums.ui.model.AlbumResponseModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users/{id}/albums")
public class AlbumsController {

    private final AlbumsService albumsService;
    private final Logger logger;

    @Autowired
    public AlbumsController(AlbumsService albumsService) {
        this.albumsService = albumsService;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE }
    )
    public List<AlbumResponseModel> userAlbums(@PathVariable String id) {
        List<AlbumResponseModel> returnValue = new ArrayList<>();
        List<AlbumEntity> albumsEntities = albumsService.getAlbums(id);

        if(albumsEntities == null || albumsEntities.isEmpty())
        {
            return returnValue;
        }

        Type listType = new TypeToken<List<AlbumResponseModel>>(){}.getType();

        returnValue = new ModelMapper().map(albumsEntities, listType);
        logger.info("Returning " + returnValue.size() + " albums");
        return returnValue;
    }
}
