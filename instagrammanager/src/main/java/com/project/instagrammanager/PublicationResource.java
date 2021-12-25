package com.project.instagrammanager;

import com.project.instagrammanager.model.Publication;
import com.project.instagrammanager.service.PublicationService;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/publication")
public class PublicationResource {
    private final PublicationService publicationService;

    public PublicationResource(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Publication>> getAllPublications () {
        List<Publication> publications = publicationService.findAllPublications();
        return new ResponseEntity<>(publications, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Publication> getPublicationById (@PathVariable("id") Long id) {
        Publication publication = publicationService.findPublicationById(id);
        return new ResponseEntity<>(publication, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Publication> addPublication(@RequestBody Publication publication) {
        Publication new_publication = publicationService.addPublication(publication);
        return new ResponseEntity<>(publication, HttpStatus.CREATED);
    }

    @PutMapping("/update ")
    public ResponseEntity<Publication> updatePublication(@RequestBody Publication publication) {
        Publication update_publication = publicationService.updatePublication(publication);
        return new ResponseEntity<>(update_publication, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id} ")
    public ResponseEntity<?> deletePublication(@PathVariable("id") Long id) {
        publicationService.deletePublication(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all_posted")
    public ResponseEntity<Object> getAllPosted () throws IOException, JSONException, ParseException {
        Object publications = publicationService.findAllPosted();
        return new ResponseEntity<>(publications, HttpStatus.OK);
    }
}
