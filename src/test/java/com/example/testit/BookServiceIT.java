package com.example.testit;

import com.example.testit.adapter.mail.MailService;
import com.example.testit.model.Book;
import com.example.testit.model.Member;
import com.example.testit.model.Status;
import com.example.testit.repository.BookRepository;
import com.example.testit.repository.MemberRepository;
import com.example.testit.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.Mockito;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BookServiceIT {

    BookService bookService;
    BookRepository bookRepository;     
    MemberRepository memberRepository;
    MailService mailService;

    @BeforeEach
    void setUp() {
        bookRepository = Mockito.mock(BookRepository.class);
        memberRepository = Mockito.mock(MemberRepository.class);
        mailService = Mockito.mock(MailService.class);
        bookService = new BookService(bookRepository, memberRepository, mailService);
    }
    @Test
    public void emprunterPuisRendreUnLivreEtRelireLeResultat() {
        // Given
        Member thibaut = new Member("thibaut");
        thibaut.setId(1L);
        Book livre = new Book("Les misérables", "Victor Hugo");
        livre.setId(1L);
        
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(livre));
        Mockito.when(memberRepository.findById(1L)).thenReturn(Optional.of(thibaut));
        Mockito.when(bookRepository.save(livre)).thenReturn(livre);
      
        // When / Then : emprunt
        Book livreEmprunter = bookService.borrowBook(livre.getId(), thibaut.getId());
        assertThat(livreEmprunter.getStatus()).isEqualTo(Status.EMPRUNTE);

        // When / Then : retour
        Book livreRetourner = bookService.returnBook(livre.getId(), thibaut.getId());
        assertThat(livreRetourner.getStatus()).isEqualTo(Status.RENDU);
    }
}