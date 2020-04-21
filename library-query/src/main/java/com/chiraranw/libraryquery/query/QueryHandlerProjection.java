package com.chiraranw.libraryquery.query;

import com.chiraranw.libraryquery.model.Book;
import com.chiraranw.libraryquery.model.BorrowBk;
import com.chiraranw.libraryquery.model.Member;
import com.chiraranw.libraryquery.model.ReturnBk;
import com.chiraranw.libraryquery.repository.BkBorrowRepository;
import com.chiraranw.libraryquery.repository.BkRepository;
import com.chiraranw.libraryquery.repository.BkReturnRepository;
import com.chiraranw.libraryquery.repository.MbRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class QueryHandlerProjection {

    private final BkRepository bkRepository;
    private final BkBorrowRepository bkBorrowRepository;
    private final BkReturnRepository bkReturnRepository;
    private final MbRepository mbRepository;
    private final EventStore eventStore;

    @Autowired
    public QueryHandlerProjection(BkRepository bkRepository,
                                  BkBorrowRepository bkBorrowRepository,
                                  BkReturnRepository bkReturnRepository,
                                  MbRepository mbRepository,
                                  EventStore eventStore) {
        this.bkRepository = bkRepository;
        this.bkBorrowRepository = bkBorrowRepository;
        this.bkReturnRepository = bkReturnRepository;
        this.mbRepository = mbRepository;
        this.eventStore = eventStore;
    }

    //Member
    @QueryHandler(queryName = "findOneMember")
    public Optional<Member> findOneMember(String mbId) {
        return this.mbRepository.findByMbId(mbId);
    }

    @QueryHandler(queryName = "findAllMembers")
    public List<Member> findAllMembers() {
        return this.mbRepository.findAll();
    }

    //Book
    @QueryHandler(queryName = "findOneBook")
    public Optional<Book> findOneBook(String bkId) {
        return this.bkRepository.findByBkId(bkId);
    }

    @QueryHandler(queryName = "findAllBooks")
    public List<Book> findAllBooks() {
        return this.bkRepository.findAll();
    }


    //Reporting Queries

    //1. How many book did I borrow and not returned?
    @QueryHandler(queryName = "booksNotReturned")
    public List<String> booksNotReturned(String mbId) {

        List<String> temp = new ArrayList<>();
        HashMap<String, Integer> map = new HashMap<>();
        List<String> borrows = new ArrayList<>();
        List<String> returns = new ArrayList<>();

        Optional<List<BorrowBk>> borrowBkList = this.bkBorrowRepository.findByMbId(mbId);
        borrowBkList.ifPresent(res -> {
            borrows.addAll(borrowBkList.get()
                    .stream()
                    .map(e -> e.getBkId())
                    .collect(Collectors.toList()));
        });

        Optional<List<ReturnBk>> returnBkList = this.bkReturnRepository.findByMbId(mbId);
        returnBkList.ifPresent(res -> {
            returns.addAll(
                    returnBkList.get()
                            .stream()
                            .map(e -> e.getBkId())
                            .collect(Collectors.toList()));
        });

        addToHashMap(borrows, map);
        addToHashMap(returns, map);

        map.forEach((k, v) -> {
            if (v % 2 != 0) {
                temp.add(k);
            }

        });
        return temp;
    }


    //2. What a member has done in the lib
    @QueryHandler(queryName = "memberHistory")
    public List<Object> memberHistory(String mbId) {
        return this.eventStore.readEvents(mbId)
                .asStream()
                .map(e -> {

                    System.out.println(e);

                    return e.toString();
                })
                .collect(Collectors.toList());
    }


    //Helper methods

    private void addToHashMap(List<String> strings, HashMap<String, Integer> map) {
        strings.forEach(e -> {
            System.out.println("e" + e);
            if (!map.containsKey(e)) {
                map.put(e, 1);
            } else {
                map.put(e, map.get(e) + 1);
            }
        });
    }


}
