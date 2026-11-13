package com.example.testit;

import com.example.testit.model.Book;
import com.example.testit.model.Member;
import com.example.testit.model.Status;
import com.example.testit.repository.BookRepository;
import com.example.testit.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BookRepositoryIT {

    @Autowired 
    BookRepository bookRepository;

    @Autowired 
    MemberRepository memberRepository;

    @Test
    public void findByMemberAndStatusAvecDeuxMembresEtPlusieursLivres() {
        //given
        Member thibaut = memberRepository.save(new Member("Thibaut"));
        Member rafael = memberRepository.save(new Member("Rafael"));

        Book b1 = new Book("Les misérables", "Victor Hugo");
        b1.setStatus(Status.EMPRUNTE);
        b1.setBorrower(thibaut);
        bookRepository.save(b1);

        Book b2 = new Book("Germinal", "Emile zola");
        b2.setStatus(Status.RENDU);
        b2.setBorrower(thibaut);
        bookRepository.save(b2);

        Book b3 = new Book("Echelle de Seelog, les origines", "Seelog");
        b3.setStatus(Status.EMPRUNTE);
        b3.setBorrower(rafael);
        bookRepository.save(b3);

        Book b4 = new Book("The legend of Zelda, Souvenir d'enfances", "Matthieu Meriot");
        b4.setStatus(Status.DISPONIBLE);
        b4.setBorrower(thibaut);
        bookRepository.save(b4);

        // When
        var result = bookRepository.findByMemberAndStatus(thibaut.getId(), Status.EMPRUNTE);
        var result2 = bookRepository.findByMemberAndStatus(rafael.getId(), Status.EMPRUNTE);
 
        // Then (On regarde qu'on obtient )
        assertThat(result).extracting(Book::getTitle).containsExactly("Les misérables");
        assertThat(result2).extracting(Book::getTitle).containsExactly("Echelle de Seelog, les origines");
        
    }
}
