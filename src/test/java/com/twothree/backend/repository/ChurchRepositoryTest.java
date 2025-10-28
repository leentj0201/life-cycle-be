package com.twothree.backend.repository;

import com.twothree.backend.config.TestConfig;
import com.twothree.backend.entity.Church;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestConfig.class)
@ActiveProfiles("test")
@DisplayName("ChurchRepository 테스트")
class ChurchRepositoryTest {
    
    @Autowired
    private ChurchRepository churchRepository;
    
    @Autowired
    private TestEntityManager entityManager;
    
    private Church activeChurch;
    private Church inactiveChurch;
    
    @BeforeEach
    void setUp() {
        // 활성화된 교회
        activeChurch = new Church();
        activeChurch.setId(UUID.randomUUID()); // UUID 수동 설정
        activeChurch.setName("활성화된 교회");
        activeChurch.setAddress("서울시 강남구");
        activeChurch.setPhone("02-1234-5678");
        activeChurch.setEmail("active@church.com");
        activeChurch.setWebsite("https://active-church.com");
        activeChurch.setPastorName("김목사");
        activeChurch.setDescription("활성화된 교회입니다");
        activeChurch.setIsActive(true);
        entityManager.persistAndFlush(activeChurch);
        
        // 비활성화된 교회
        inactiveChurch = new Church();
        inactiveChurch.setId(UUID.randomUUID()); // UUID 수동 설정
        inactiveChurch.setName("비활성화된 교회");
        inactiveChurch.setAddress("서울시 서초구");
        inactiveChurch.setPhone("02-9876-5432");
        inactiveChurch.setEmail("inactive@church.com");
        inactiveChurch.setIsActive(false);
        entityManager.persistAndFlush(inactiveChurch);
        
        entityManager.clear();
    }
    
    @Test
    @DisplayName("활성화된 교회 목록 조회")
    void findByIsActiveTrue() {
        // when
        List<Church> activeChurches = churchRepository.findByIsActiveTrue();
        
        // then
        assertThat(activeChurches).isNotEmpty();
        assertThat(activeChurches).anyMatch(c -> c.getName().equals("활성화된 교회"));
        assertThat(activeChurches).allMatch(c -> c.getIsActive().equals(true));
    }
    
    @Test
    @DisplayName("ID로 활성화된 교회 조회 - 성공")
    void findByIdAndIsActiveTrue_Success() {
        // when
        Optional<Church> found = churchRepository.findByIdAndIsActiveTrue(activeChurch.getId());
        
        // then
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("활성화된 교회");
        assertThat(found.get().getIsActive()).isTrue();
    }
    
    @Test
    @DisplayName("ID로 활성화된 교회 조회 - 비활성화된 교회는 조회되지 않음")
    void findByIdAndIsActiveTrue_InactiveChurch() {
        // when
        Optional<Church> found = churchRepository.findByIdAndIsActiveTrue(inactiveChurch.getId());
        
        // then
        assertThat(found).isEmpty();
    }
    
    @Test
    @DisplayName("교회 저장")
    void saveChurch() {
        // given
        Church newChurch = new Church();
        newChurch.setId(UUID.randomUUID()); // UUID 수동 설정
        newChurch.setName("새로운 교회");
        newChurch.setAddress("서울시 송파구");
        newChurch.setPhone("02-1111-2222");
        newChurch.setEmail("new@church.com");
        newChurch.setIsActive(true);
        
        // when
        Church saved = churchRepository.save(newChurch);
        entityManager.flush();
        
        // then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("새로운 교회");
        
        Church found = entityManager.find(Church.class, saved.getId());
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("새로운 교회");
    }
    
    @Test
    @DisplayName("교회 정보 수정")
    void updateChurch() {
        // given
        Church church = entityManager.find(Church.class, activeChurch.getId());
        
        // when
        church.setName("수정된 교회");
        church.setPastorName("이목사");
        churchRepository.save(church);
        entityManager.flush();
        entityManager.clear();
        
        // then
        Church found = entityManager.find(Church.class, activeChurch.getId());
        assertThat(found.getName()).isEqualTo("수정된 교회");
        assertThat(found.getPastorName()).isEqualTo("이목사");
    }
    
    @Test
    @DisplayName("교회 삭제 (논리 삭제)")
    void deleteChurch() {
        // given
        Church church = entityManager.find(Church.class, activeChurch.getId());
        
        // when
        church.setIsActive(false);
        churchRepository.save(church);
        entityManager.flush();
        entityManager.clear();
        
        // then
        // 비활성화된 교회는 활성화된 목록에서 조회되지 않음
        Optional<Church> notFound = churchRepository.findByIdAndIsActiveTrue(activeChurch.getId());
        assertThat(notFound).isEmpty();
        
        // 실제로는 DB에 존재
        Church found = entityManager.find(Church.class, activeChurch.getId());
        assertThat(found).isNotNull();
        assertThat(found.getIsActive()).isFalse();
    }
}

