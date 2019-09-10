package ru.oooit;

import jBittorrentAPI.DownloadManager;
import jBittorrentAPI.TorrentFile;
import jBittorrentAPI.TorrentProcessor;
import jBittorrentAPI.Utils;

import java.util.logging.Level;
import java.util.logging.Logger;


public class NewTorrenFile {

    public static void NewTorrent(String torrentPath) {

        // Взять торрент-файл
        TorrentProcessor tp = new TorrentProcessor();
        TorrentFile tf = tp.getTorrentFile(tp.parseTorrent(torrentPath));
        DownloadManager dm = new DownloadManager(tf, Utils.generateID());

        // Запуск закачки
        dm.startListening(6882, 6889);
        dm.startTrackerUpdate();

        while(true)
        {
            // Если загрузка завершена, то ожидание прерывается
            if(dm.isComplete())
            {
                break;
            }

            try
            {
                Thread.sleep(1000);
            }
            catch(InterruptedException ex)
            {
                Logger.getLogger(NewTorrenFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // завершение закачки
        dm.stopTrackerUpdate();
        dm.closeTempFiles();

        // проверка, куда были сохранены скачанные данные (то поле, которое задается в TorrentProcessor.setName())
        String torrentSavedTo = tp.getTorrentFile(tp.parseTorrent(torrentPath)).saveAs;
        System.out.println(torrentSavedTo);
    }

}