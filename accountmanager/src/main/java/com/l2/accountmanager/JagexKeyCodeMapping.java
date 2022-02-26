package com.l2.accountmanager;

import lombok.Getter;

import java.awt.event.KeyEvent;

public enum JagexKeyCodeMapping
{
	KC_F1(1, KeyEvent.VK_F1),
	KC_F2(2, KeyEvent.VK_F2),
	KC_F3(3, KeyEvent.VK_F3),
	KC_F4(4, KeyEvent.VK_F4),
	KC_F5(5, KeyEvent.VK_F5),
	KC_F6(6, KeyEvent.VK_F6),
	KC_F7(7, KeyEvent.VK_F7),
	KC_F8(8, KeyEvent.VK_F8),
	KC_F9(9, KeyEvent.VK_F9),
	KC_F10(10, KeyEvent.VK_F10),
	KC_F11(11, KeyEvent.VK_F11),
	KC_F12(12, KeyEvent.VK_F12),
	KC_ESCAPE(13, KeyEvent.VK_ESCAPE),

	KC_1(16, KeyEvent.VK_1),
	KC_2(17, KeyEvent.VK_2),
	KC_3(18, KeyEvent.VK_3),
	KC_4(19, KeyEvent.VK_4),
	KC_5(20, KeyEvent.VK_5),
	KC_6(21, KeyEvent.VK_6),
	KC_7(22, KeyEvent.VK_7),
	KC_8(23, KeyEvent.VK_8),
	KC_9(24, KeyEvent.VK_9),
	KC_0(25, KeyEvent.VK_0),
	KC_MINUS(26, KeyEvent.VK_MINUS),
	KC_EQUALS(27, KeyEvent.VK_EQUALS),
	KC_TILDE(28, KeyEvent.VK_DEAD_TILDE),

	KC_Q(32, KeyEvent.VK_Q),
	KC_W(33, KeyEvent.VK_W),
	KC_E(34, KeyEvent.VK_E),
	KC_R(35, KeyEvent.VK_R),
	KC_T(36, KeyEvent.VK_T),
	KC_Y(37, KeyEvent.VK_Y),
	KC_U(38, KeyEvent.VK_U),
	KC_I(39, KeyEvent.VK_I),
	KC_O(40, KeyEvent.VK_O),
	KC_P(41, KeyEvent.VK_P),
	KC_OPEN_BRACKET(42, KeyEvent.VK_OPEN_BRACKET),
	KC_CLOSE_BRACKET(43, KeyEvent.VK_CLOSE_BRACKET),

	KC_A(48, KeyEvent.VK_A),
	KC_S(49, KeyEvent.VK_S),
	KC_D(50, KeyEvent.VK_D),
	KC_F(51, KeyEvent.VK_F),
	KC_G(52, KeyEvent.VK_G),
	KC_H(53, KeyEvent.VK_H),
	KC_J(54, KeyEvent.VK_J),
	KC_K(55, KeyEvent.VK_K),
	KC_L(56, KeyEvent.VK_L),
	KC_SEMICOLON(57, KeyEvent.VK_SEMICOLON),
	KC_QUOTE(58, KeyEvent.VK_QUOTE),

	KC_Z(64, KeyEvent.VK_Z),
	KC_X(65, KeyEvent.VK_X),
	KC_C(66, KeyEvent.VK_C),
	KC_V(67, KeyEvent.VK_V),
	KC_B(68, KeyEvent.VK_B),
	KC_N(69, KeyEvent.VK_N),
	KC_M(70, KeyEvent.VK_M),
	KC_COMMA(71, KeyEvent.VK_COMMA),
	KC_PERIOD(72, KeyEvent.VK_PERIOD),
	KC_FORWARD_SLASH(73, KeyEvent.VK_SLASH),
	KC_BACK_SLASH(74, KeyEvent.VK_BACK_SLASH),

	KC_TAB(80, KeyEvent.VK_TAB),
	KC_SHIFT(81, KeyEvent.VK_SHIFT),
	KC_CONTROL(82, KeyEvent.VK_CONTROL),
	KC_SPACE(83, KeyEvent.VK_SPACE),
	KC_ENTER(84, KeyEvent.VK_ENTER),
	KC_BACKSPACE(85, KeyEvent.VK_BACK_SPACE),
	KC_ALT(86, KeyEvent.VK_ALT),

	KC_LEFT(96, KeyEvent.VK_LEFT),
	KC_RIGHT(97, KeyEvent.VK_RIGHT),
	KC_UP(98, KeyEvent.VK_UP),
	KC_DOWN(99, KeyEvent.VK_LEFT),
	KC_INSERT(100, KeyEvent.VK_INSERT),
	KC_DELETE(101, KeyEvent.VK_DELETE),
	KC_HOME(102, KeyEvent.VK_HOME),
	KC_END(103, KeyEvent.VK_END),
	KC_PAGE_UP(104, KeyEvent.VK_PAGE_UP),
	KC_PAGE_DOWN(105, KeyEvent.VK_PAGE_DOWN);

	@Getter
	int jagexKeyCode;
	@Getter
	int javaKeyCode;

	JagexKeyCodeMapping(int jagex, int java)
	{
		jagexKeyCode = jagex;
		javaKeyCode = java;
	}
}
