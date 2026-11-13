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
        Member thibaut = new Member("Thibaut");
        Member rafael = new Member("Rafael");

        Book b1 = new Book("Les misérables", "Victor Hugo");
        b1.setStatus(Status.EMPRUNTE);
        b1.setBorrower(thibaut);
        bookRepository.save(b1);
        
        Book b2 = new Book("Germinal", "Emile zola");
        Book b3 = new Book("Echelle de Seelog, les origines", "Seelog");
        Book b4 = new Book("The legend of Zelda, Souvenir d'enfances", "Matthieu Meriot");

        
    }
}
