package com.example.coupleapp.couple.service;

import com.example.coupleapp.common.exception.ApiException;
import com.example.coupleapp.couple.dto.CoupleLinkResponse;
import com.example.coupleapp.couple.dto.SendInviteRequest;
import com.example.coupleapp.couple.entity.CoupleLink;
import com.example.coupleapp.couple.entity.CoupleStatus;
import com.example.coupleapp.couple.repository.CoupleLinkRepository;
import com.example.coupleapp.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CoupleService {

    private final CoupleLinkRepository coupleLinkRepository;
    private final UserRepository userRepository;

    public CoupleService(CoupleLinkRepository coupleLinkRepository, UserRepository userRepository) {
        this.coupleLinkRepository = coupleLinkRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public CoupleLinkResponse sendInvite(Long currentUserId, SendInviteRequest request) {
        var partner = userRepository.findByEmailIgnoreCase(request.partnerEmail())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Partner not found"));

        if (currentUserId.equals(partner.getId())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Cannot invite yourself");
        }

        if (hasActiveLink(currentUserId) || hasActiveLink(partner.getId())) {
            throw new ApiException(HttpStatus.CONFLICT, "One user already has an active couple link");
        }

        boolean alreadyPending = coupleLinkRepository.existsByStatusAndUser1IdAndUser2Id(
                CoupleStatus.PENDING,
                currentUserId,
                partner.getId()
        );
        if (alreadyPending) {
            throw new ApiException(HttpStatus.CONFLICT, "Invite already sent");
        }

        CoupleLink link = new CoupleLink();
        link.setUser1Id(currentUserId);
        link.setUser2Id(partner.getId());
        link.setStatus(CoupleStatus.PENDING);
        CoupleLink saved = coupleLinkRepository.save(link);

        return toResponse(saved);
    }

    @Transactional
    public CoupleLinkResponse acceptInvite(Long currentUserId, Long inviteId) {
        CoupleLink invite = coupleLinkRepository.findById(inviteId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Invite not found"));

        if (!invite.getUser2Id().equals(currentUserId)) {
            throw new ApiException(HttpStatus.FORBIDDEN, "Only the recipient can accept invite");
        }
        if (invite.getStatus() != CoupleStatus.PENDING) {
            throw new ApiException(HttpStatus.CONFLICT, "Invite is not pending");
        }

        invite.setStatus(CoupleStatus.ACTIVE);
        return toResponse(coupleLinkRepository.save(invite));
    }

    public CoupleLinkResponse activeLink(Long currentUserId) {
        CoupleLink active = coupleLinkRepository
                .findByStatusAndUserId(CoupleStatus.ACTIVE, currentUserId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "No active couple link"));
        return toResponse(active);
    }

    private boolean hasActiveLink(Long userId) {
        return coupleLinkRepository
                .findByStatusAndUserId(CoupleStatus.ACTIVE, userId)
                .isPresent();
    }

    private CoupleLinkResponse toResponse(CoupleLink link) {
        return new CoupleLinkResponse(link.getId(), link.getUser1Id(), link.getUser2Id(), link.getStatus());
    }
}
