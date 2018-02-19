package ru.site.mysite.mysite;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.site.mysite.mysite.Entitys.MtsDB;

@Repository
@Transactional
public interface MtsRepository extends JpaRepository<MtsDB, Long> {
}
