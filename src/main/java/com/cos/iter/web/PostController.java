package com.cos.iter.web;

import com.cos.iter.config.auth.LoginUserAnnotation;
import com.cos.iter.config.auth.dto.LoginUser;
import com.cos.iter.domain.post.Post;
import com.cos.iter.service.PostService;
import com.cos.iter.util.Logging;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
public class PostController {
    private final PostService postService;
    private final Logging logging;

    @GetMapping({"", "/"})
    public String feed(String tag, @LoginUserAnnotation LoginUser loginUser,
                       @RequestParam(name = "page", defaultValue = "1") int page, Model model) {
        log.info(logging.getClassName() + " / " + logging.getMethodName());
        log.info("loginUser : " + loginUser);

        model.addAttribute("posts", postService.getFeedPhoto(loginUser.getId(), tag, page));
        return "image/feed";
    }

    @GetMapping("/post/feed/test")
    public @ResponseBody
    List<Post> testFeed(String tag, @LoginUserAnnotation LoginUser loginUser,
                        @RequestParam(name = "page", defaultValue = "1") int page) {
        log.info(logging.getClassName() + " / " + logging.getMethodName());

        return postService.getFeedPhoto(loginUser.getId(), tag, page);
    }

    @GetMapping("/post/uploadForm")
    public String imageUploadForm(@RequestParam(name = "location", required = false) String location, Model model) {
        log.info(logging.getClassName() + " / " + logging.getMethodName());

        if(location == null) {
            log.info("Redirecting to location find page");
            return "redirect:/location/find";
        }

        model.addAttribute("location", location);
        return "image/image-upload";
    }

    @GetMapping("/post/explore")
    public String imageExplore(@LoginUserAnnotation LoginUser loginUser,
                               @RequestParam(name = "page", defaultValue = "1") int page, Model model) {
        log.info(logging.getClassName() + " / " + logging.getMethodName());

        model.addAttribute("posts", postService.getPopularPost(loginUser.getId(), page));
        return "image/explore";
    }

}
