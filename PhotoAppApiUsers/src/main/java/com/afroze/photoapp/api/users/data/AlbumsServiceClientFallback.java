package com.afroze.photoapp.api.users.data;

import com.afroze.photoapp.api.users.ui.model.AlbumResponseModel;

import java.util.ArrayList;
import java.util.List;

public class AlbumsServiceClientFallback implements AlbumsServiceClient {

    @Override
    public List<AlbumResponseModel> getAlbums(String id) {
        return new ArrayList<>();
    }
}
