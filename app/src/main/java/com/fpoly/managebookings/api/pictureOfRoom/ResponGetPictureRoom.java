package com.fpoly.managebookings.api.pictureOfRoom;

import com.fpoly.managebookings.models.RoomDetail;
import com.fpoly.managebookings.models.picture.PictureOfRoom;

import java.util.List;

public interface ResponGetPictureRoom {
    void responsePicture(PictureOfRoom pictureOfRoom);
    void responseListPicture(List<PictureOfRoom> imageRooms);
}
