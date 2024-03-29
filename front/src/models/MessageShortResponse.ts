// private Long id;
// private String senderUsername;
// private String text;
// private LocalDateTime dateTime;

export interface MessageShortResponse {
    id: number;
    senderUsername: string;
    text: string;
    dateTime: Date;
}