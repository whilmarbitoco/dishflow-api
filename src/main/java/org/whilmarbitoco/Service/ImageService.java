package org.whilmarbitoco.Service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.BadRequestException;
import org.jboss.resteasy.reactive.multipart.FileUpload;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;


@ApplicationScoped
public class ImageService {

    private static final long MAX_SIZE = 5 * 1024 * 1024; // 5MB
    private static final int MAX_WIDTH = 1920;
    private static final int MAX_HEIGHT = 1080;

    public void validate(FileUpload file) {
        try {
            String mimeType = file.contentType();
            if (mimeType == null || !mimeType.startsWith("image/")) {
                throw new BadRequestException("Invalid file type " + mimeType);
            }

            if (file.size() > MAX_SIZE) {
                throw new BadRequestException("File too large: " + file.size());
            }

            BufferedImage img = ImageIO.read(file.filePath().toFile());
            if (img == null) {
                throw new BadRequestException("Invalid image file: " + file.fileName());
            }

            if (img.getWidth() > MAX_WIDTH || img.getHeight() > MAX_HEIGHT) {
                throw new BadRequestException("Image dimensions exceed limit: " + img.getWidth() + "x" + img.getHeight());
            }

        } catch (IOException e) {
            throw new BadRequestException("Something went wrong while validating the image.");
        }
    }

    public void saveFile(FileUpload file) {
        try {

            String filename = "wb2c0-dish-flow-ini-" + UUID.randomUUID() + ".png";
            Path destination = Path.of(System.getProperty("user.dir") + "/src/main/resources/images", filename);
            Files.move(file.filePath(), destination);

        } catch (IOException e) {
            throw new BadRequestException("Something went wrong while saving the image.");
        }
    }
}
