package com.example.testit;

import com.example.testit.adapter.mail.MailService;
import com.example.testit.model.Book;
import com.example.testit.model.Member;
import com.example.testit.model.Status;
import com.example.testit.repository.BookRepository;
import com.example.testit.repository.MemberRepository;
import com.example.testit.service.BookService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

class BookServiceTest {

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
    public void EmprunterUnLivreDispoVerifierLemprunteurEtLesMails() {
        // Given
        Book livre = new Book("Les misérables", "Victor Hugo");
        livre.setId(1L);
        Member member = new Member("Jean");
        member.setId(2L);

        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(livre));
        Mockito.when(memberRepository.findById(2L)).thenReturn(Optional.of(member));
        Mockito.when(bookRepository.findByMemberAndStatus(Mockito.any(), Mockito.any())).thenReturn(List.of());
        Mockito.when(bookRepository.save(livre)).thenReturn(livre);
      
        // When
        var emprunter = bookService.borrowBook(1L, 2L);

        // Then
        Assertions.assertThat(emprunter.getStatus()).isEqualTo(Status.EMPRUNTE);
        Assertions.assertThat(emprunter.getBorrower()).isSameAs(member);
        Mockito.verify(mailService).sendMail(Mockito.any(), Mockito.any(), Mockito.any());
    }
}