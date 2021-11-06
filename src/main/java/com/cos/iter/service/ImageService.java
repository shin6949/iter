package com.cos.iter.service;

import com.cos.iter.domain.image.Image;
import com.cos.iter.domain.image.ImageRepository;
import com.cos.iter.domain.post.Post;
import com.cos.iter.domain.post.PostRepository;
import com.cos.iter.domain.tag.Tag;
import com.cos.iter.domain.tag.TagRepository;
import com.cos.iter.util.TagParser;
import com.cos.iter.web.dto.ImageReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
@Log4j2
public class ImageService {
	private final ImageRepository imageRepository;
	private final TagRepository tagRepository;
	private final AzureService azureService;
	private final PostRepository postRepository;
	private final TagParser tagParser;

	@Transactional
	public void photoUploadToCloud(ImageReqDto imageReqDto, int postId) {
		final Post postEntity = postRepository.findById(postId).orElseThrow(null);

		short seq = 0;
		for(MultipartFile file : imageReqDto.getFile()) {
			try {
				final String imageFilename = azureService.uploadToCloudAndReturnFileName(file, "photo");
				final Image image = imageReqDto.toImageEntity(imageFilename, postEntity, seq);
				imageRepository.save(image);
				seq += 1;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Tag 저장 -> Tag는 Post에 종속되어 있으므로 한 번만 등록하면 됨.
		List<String> tagNames = tagParser.tagParse(imageReqDto.getTags());
		for (String name : tagNames) {
			Tag tag = Tag.builder()
					.post(postEntity)
					.name(name)
					.build();
			tagRepository.save(tag);
		}
	}
}