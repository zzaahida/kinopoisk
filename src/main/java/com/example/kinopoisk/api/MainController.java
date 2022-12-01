package com.example.kinopoisk.api;


import com.example.kinopoisk.dto.UserRegistrationDto;
import com.example.kinopoisk.model.Movie;
import com.example.kinopoisk.model.Role;
import com.example.kinopoisk.model.User;
import com.example.kinopoisk.repo.MovieRepo;
import com.example.kinopoisk.repo.RoleRepo;
import com.example.kinopoisk.repo.UserRepo;
import com.example.kinopoisk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.*;

@Controller
public class MainController {

    @Autowired
    private MovieRepo movieRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Value("${uploadDir}")
    private String uploadFolder;



    @GetMapping("/")
    public String index(Model model, String keyword) {
        if (keyword != null) {
            List<Movie> movies = movieRepo.findByKeyword(keyword);
            model.addAttribute("movies", movies);
        }
        else {
            List<Movie> movies = movieRepo.findAll();
            model.addAttribute("movies", movies);
        }
        return "main";
    }

    @GetMapping("/users")
    public String users(Model model) {
            Iterable<User> users = userRepo.findAll();
            model.addAttribute("users", users);
            return "user-list";
    }

    @PostMapping("/users")
    public String adminControl(@RequestParam("id") Long id, @RequestParam(value = "isAdmin", required = false) String isAdmin) {
        User user = userRepo.findById(id).orElseThrow();
        Set<Role> roles = new HashSet<>();

        roles.add(roleRepo.findById(1L).orElseThrow());

        if(isAdmin != null){
            user.setAdmin(true);

            roles.add(roleRepo.findById(2L).orElseThrow());
        }else{
            user.setAdmin(false);
        }

        user.setRoles(roles);
        userRepo.save(user);

        return "redirect:/users";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping("/admin/add-movie")
    public String addmovie() {
        return "add-movie";
    }

    @PostMapping ("/admin/add-movie")
    public String
    addmoviepost(Model model, @RequestParam("name") String name, @RequestParam("description") String description,
                 @RequestParam("link") String link,
                 HttpServletRequest request, final @RequestParam("img")MultipartFile file) throws IOException {
        try {
            String uploadDirectoryAnalytics = request.getServletContext().getRealPath(uploadFolder);
            String fileName = file.getOriginalFilename();
            String filePath = Paths.get(uploadDirectoryAnalytics, fileName).toString();
            String[] names = name.split(",");
            String[] descriptions = description.split(",");
            String[] links = link.split(",");
            try {
                File dir = new File(uploadDirectoryAnalytics);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
                stream.write(file.getBytes());
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            byte[] imageData = file.getBytes();
            Movie movie = new Movie();
            movie.setName(name);
            movie.setDescription(description);
            movie.setLink(link);
            movie.setImg(imageData);
            movieRepo.save(movie);
            return "redirect:/";
        }catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }
    }
    @GetMapping("/movies/display/{id}")
    @ResponseBody
    void showImage(@PathVariable("id") Long id, HttpServletResponse response, Optional<Movie> movies)
            throws ServletException, IOException {
        movies = movieRepo.findById(id);
        response.getOutputStream().write(movies.get().getImg());
        response.getOutputStream().close();
    }
}
