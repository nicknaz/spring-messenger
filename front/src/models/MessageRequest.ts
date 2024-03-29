// private Long id;
// private String senderUsername;
// private String text;
// private LocalDateTime dateTime;

export interface MessageRequest {
    id: number;
    text: string;
    dateTime: Date;
}