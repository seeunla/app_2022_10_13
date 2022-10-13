package com.ll.exam.app__2022_10_05;

import com.ll.exam.app__2022_10_05.app.cacheTest.dto.Person;
import com.ll.exam.app__2022_10_05.app.cacheTest.service.CacheTestService;
import com.ll.exam.app__2022_10_05.app.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class CacheTests {
    @Autowired
    private CacheTestService cacheTestService;

    @Test
    @DisplayName("캐시 사용")
    void t1() throws Exception {
        int rs = cacheTestService.getCachedInt();
        System.out.println(rs);

        rs = cacheTestService.getCachedInt();
        System.out.println(rs);

        System.out.println(rs);
    }

    @Test
    @DisplayName("캐시 삭제")
    void t2() throws Exception {
        int rs = cacheTestService.getCachedInt();

        System.out.println(rs);

        cacheTestService.deleteCachedInt();

        rs = cacheTestService.getCachedInt();

        System.out.println(rs);
    }

    @Test
    @DisplayName("캐시 수정")
    void t3() throws Exception {
        int rs = cacheTestService.getCachedInt();

        assertThat(rs).isGreaterThan(0);
        System.out.println(rs);

        cacheTestService.modifyCachedInt();

        rs = cacheTestService.getCachedInt();

        System.out.println(rs);
    }

    @Test
    @DisplayName("더하기 캐시")
    void t4() throws Exception {
        int rs = cacheTestService.getCachedInt();
        System.out.println(rs);

        cacheTestService.plus(3, 6); // 9 // 실행(캐시생성)
        cacheTestService.plus(3, 6); // 9 // 캐시사용
        cacheTestService.plus(5, 2); // 7 // 실행(캐시생성)
        cacheTestService.plus(5, 2); // 7 // 캐시사용

        rs = cacheTestService.getCachedInt();
        System.out.println(rs);
    }

    @Test
    @DisplayName("래퍼런스 매개변수")
    void t5() throws Exception {
        Person p1 = new Person(1, "홍길동1");
        Person p2 = new Person(1, "홍길동2");

        System.out.println(p1.equals(p2));

        String personName = cacheTestService.getName(p1, 5);
        System.out.println(personName);

        personName = cacheTestService.getName(p2, 10);
        System.out.println(personName);
    }
}
