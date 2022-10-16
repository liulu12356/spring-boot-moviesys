package com.qf.moviesys.web;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qf.moviesys.pojo.Category;
import com.qf.moviesys.pojo.Movie;
import com.qf.moviesys.service.MoviesService;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@Log4j2
@CrossOrigin
public class MoviesController {

    @Autowired
    MoviesService moviesService;


    @GetMapping("/movie")
    List<Movie> findAll() {
        return moviesService.findAll();
    }

    @GetMapping("/movie/page")
    IPage<Movie> findByPage(Integer current, Integer size) {
        return moviesService.findByPage(current,size);
    }


    @GetMapping("/movie/{id}")
    Movie findById(@PathVariable("id") Integer id) {
        Movie movie = moviesService.findById(id);
        final List<Category> categoryListByMovie = moviesService.findCategoryListByMovie(id);
        movie.setCategoryList(categoryListByMovie);
        return movie;
    }

    @GetMapping("/movie/findByTitle/{title}")
    List<Movie> findByTitle(@PathVariable("title") String title) {
        return moviesService.findByTitle("%"+title+"%");
    }

    //根据分页模糊查询
    @GetMapping("movie/findByKeyword/page/{keyword}")
    IPage<Movie> findByKeywordPage(Integer current, Integer size, @PathVariable("keyword") String keyword){
        return moviesService.findByKeywordPage(current,size,"%"+keyword+"%");
    }

    //未排档电影根据标题查询
    @GetMapping("/movie/findByTitle/noGear/{title}")
    IPage<Movie> findByTitleNoGear(@PathVariable("title") String title,Integer current,Integer size) {
        return moviesService.findByTitlePageNoGear(current,size,"%"+title+"%");
    }

    @GetMapping("/movie/findByCategory/{categoryId}")
    List<Movie> findMovieByCategory(@PathVariable("categoryId") List<Integer> categoryIdList) {
        return moviesService.findByCategory(categoryIdList);
    }

    @GetMapping("/movie/findByCategory/page/{categoryId}")
    IPage<Movie> findMovieByCategoryPage(@PathVariable("categoryId") List<Integer> categoryIdList,Integer current,Integer size) {
        System.out.println(categoryIdList);
        return moviesService.findByCategoryPage(current,size,categoryIdList);
    }



    //未排档电影根据类别查询
    @GetMapping("/movie/findByCategory/noGear/{categoryId}")
    List<Movie> findMovieByCategoryNoGear(@PathVariable("categoryId") List<Integer> categoryIdList) {
        return moviesService.findByCategoryNoGear(categoryIdList);
    }

    @PostMapping("/movie")
    String insertMovie(String title, MultipartFile uploadPic, String description, String detail, Integer state, @RequestParam("categoryIdList") List<Integer> categoryIdList) {
        Movie movie=new Movie(title,description,detail,state);
        //新增中间表的数据

        try {
        String path = "E:\\myproject\\movie-sys\\src\\main\\resources\\static\\img\\" + uploadPic.getOriginalFilename();
        movie.setPath("http://localhost:8080/img/" + uploadPic.getOriginalFilename());
        // 写入磁盘
            writeToDisk(uploadPic, path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        moviesService.insertMovie(movie,categoryIdList);
        return  "SaveOK";
    }

    @PostMapping("/movie/uploadPic")
    String uploadPic(MultipartFile uploadPic, @Param("movieName") String movieName) {
        //新增中间表的数据
        try {
            System.out.println(movieName);
            String path = "E:\\myproject\\movie-sys\\src\\main\\resources\\static\\img\\" + uploadPic.getOriginalFilename();
            // 写入磁盘
            writeToDisk(uploadPic, path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  "OK";
    }






    @PutMapping("/movie")
    String updateMovie(@RequestBody Movie movie) {
        moviesService.updateMovie(movie,movie.getCategoryIdList());
        return "UpdateOK";
    }

    @GetMapping("/movie/updateStatus/{id}")
    void updateStatus(@PathVariable("id") Integer id){
        System.out.println(id);
        moviesService.updateStatus(id);
    }

    @DeleteMapping("/movie/{id}")
    String deleteMovie(@PathVariable("id") Integer id) {
        moviesService.deleteMovie(id);
        return "OK";

    }

    private void writeToDisk(MultipartFile uploadPic, String path) throws IOException {
        File dest = new File(path);
        uploadPic.transferTo(dest);
    }

}
