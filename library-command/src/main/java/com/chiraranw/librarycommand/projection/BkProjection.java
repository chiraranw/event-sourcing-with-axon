package com.chiraranw.librarycommand.projection;

import com.chiraranw.librarycommand.coreapi.*;
import com.chiraranw.librarycommand.model.Book;
import com.chiraranw.librarycommand.model.BorrowBk;
import com.chiraranw.librarycommand.model.Member;
import com.chiraranw.librarycommand.model.ReturnBk;
import com.chiraranw.librarycommand.repository.BkBorrowRepository;
import com.chiraranw.librarycommand.repository.BkRepository;
import com.chiraranw.librarycommand.repository.BkReturnRepository;
import com.chiraranw.librarycommand.repository.MbRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
public class BkProjection {

    private final BkRepository bkRepository;
    private final MbRepository mbRepository;
    private final BkReturnRepository bkReturnRepository;
    private final BkBorrowRepository bkBorrowRepository;

    @Autowired
    public BkProjection(BkRepository bkRepository,
                        MbRepository mbRepository,
                        BkReturnRepository bkReturnRepository,
                        BkBorrowRepository bkBorrowRepository) {
        this.bkRepository = bkRepository;
        this.mbRepository = mbRepository;
        this.bkReturnRepository = bkReturnRepository;
        this.bkBorrowRepository = bkBorrowRepository;
    }


    //Book

    @EventHandler
    public void on(BkAddedEvent event) {
        //We don't validate events
        System.out.println("Applying the event {}"+event);
        this.bkRepository.save(new Book(null, event.getBkId(), event.getTitle(), event.getStatus()));
    }

    @EventHandler
    public void on(BkRemovedEvent event) {
        System.out.println("Applying the event {}"+event);
        Optional<Book> optionalBook = this.bkRepository.findByBkId(event.getBkId());
        if (optionalBook.isPresent()) {
            optionalBook.get()
                    .setStatus(event.getStatus());//unavailable
        } else {
            throw new IllegalStateException("Could not find the requested book: " + event.getBkId());
        }
    }

    @EventHandler
    @Transactional
    public void on(BkBorrowedEvent event) {
        System.out.println("Applying the event {}"+event);
        Optional<Book> optionalBook = this.bkRepository.findByBkId(event.getBkId());
        if (optionalBook.isPresent()) {
            optionalBook.get().setStatus(event.getStatus());//borrowed
            this.bkBorrowRepository.save(new BorrowBk(null, event.getBkId(), event.getMbId()));
        } else {
            throw new IllegalStateException("Could not find the requested book: " + event.getBkId());
        }
    }

    @EventHandler
    @Transactional
    public void on(BkReturnedEvent event) {
        System.out.println("Applying the event {}"+event);
        Optional<Book> optionalBook = this.bkRepository.findByBkId(event.getBkId());
        if (optionalBook.isPresent()) {
            optionalBook.get().setStatus(event.getStatus());//available
            this.bkReturnRepository.save(new ReturnBk(null, event.getBkId(), event.getMbId()));
        } else {
            throw new IllegalStateException("Could not find the requested book: " + event.getBkId());
        }
    }

    //Member
    @EventHandler
    public void on(MemberAddedEvent event) {
        System.out.println("Applying the event {}"+event);
        this.mbRepository.save(new Member(null,event.getMbId(),event.getName(),event.getBksBorrowed(),event.getStatus()));
    }


    @EventHandler
    @Transactional
    public void on(MemberRemovedEvent event) {
        System.out.println("Applying the event {}"+event);
        Optional<Member>  optionalMember=this.mbRepository.findByMbId(event.getMbId());
        if (optionalMember.isPresent()) {
            optionalMember.get().setStatus(event.getStatus());//inactive
        } else {
            throw new IllegalStateException("Could not find the requested member: " + event.getMbId());
        }
    }

}
