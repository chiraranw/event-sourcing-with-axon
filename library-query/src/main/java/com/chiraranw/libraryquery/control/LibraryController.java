package com.chiraranw.libraryquery.control;

import com.chiraranw.libraryquery.model.Book;
import com.chiraranw.libraryquery.model.Member;
import io.netty.util.concurrent.CompleteFuture;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/lib")
public class LibraryController {

    private final QueryGateway queryGateway;

    @Autowired
    public LibraryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @RequestMapping(value = "/members/{mbId}", method = RequestMethod.GET)
    public CompletableFuture<Member> getMember(@PathVariable("mbId") String mbId) {
        return this.queryGateway.query("findOneMember", mbId, Member.class);
    }

    @RequestMapping(value = "/members/all", method = RequestMethod.GET)
    public CompletableFuture<List<Member>> getAllMember() {
        return this.queryGateway.query("findAllMembers", null, ResponseTypes.multipleInstancesOf(Member.class));
    }

    @RequestMapping(value = "/memberHistory/{mbId}", method = RequestMethod.GET)
    public CompletableFuture<List<Object>> getMemberHistory(@PathVariable("mbId") String mbId) {
        return this.queryGateway.query("memberHistory", mbId, ResponseTypes.multipleInstancesOf(Object.class));
    }



    @RequestMapping(value = "/books/{bkId}")
    public CompletableFuture<Book> getBook(@PathVariable("bkId") String bkId) {
        return this.queryGateway.query("findOneBook", bkId, Book.class);
    }

    @RequestMapping(value = "/books/all", method = RequestMethod.GET)
    public CompletableFuture<List<Book>> getAllBooks() {
        return this.queryGateway.query("findAllBooks", null, ResponseTypes.multipleInstancesOf(Book.class));
    }

    @RequestMapping(value = "/booksNotReturned/{mbId}", method = RequestMethod.GET)
    public CompletableFuture<List<Object>> booksNotReturned(@PathVariable("mbId") String mbId) {
        return this.queryGateway.query("booksNotReturned", mbId, ResponseTypes.multipleInstancesOf(Object.class));
    }


}
