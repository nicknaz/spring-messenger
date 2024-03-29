export default class StickerService {
    static stickers : string[] = ["1", "2", "3", "4"];

    static messageIsSticker(message : string): boolean {
        let regexp = /#sticker#/;
        return message.search(regexp) == 0;
    }

    static getStickerNumber(message : string): number {
        let regexp = /#sticker#/;
        return +message.replace(regexp, "");
    }

}