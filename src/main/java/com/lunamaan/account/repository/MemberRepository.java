package com.lunamaan.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lunamaan.account.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	Member findByMSocialIdAndMType(String mSocialId, int mType);
}
