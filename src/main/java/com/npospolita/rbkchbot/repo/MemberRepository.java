package com.npospolita.rbkchbot.repo;

import com.npospolita.rbkchbot.domain.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Integer> {
}
