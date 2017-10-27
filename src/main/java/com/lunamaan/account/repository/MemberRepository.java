package com.lunamaan.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lunamaan.account.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	Member findByMSocialIdAndMSocialType(String mSocialId, int mSocialType);
}
