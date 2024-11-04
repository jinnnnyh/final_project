// src/components/FileUpload.js

// eslint-disable-next-line no-unused-vars
import React, { useState } from 'react';
import axios from 'axios';

const FileUpload = () => {
    const [eventTitle, setEventTitle] = useState('');
    const [eventContent, setEventContent] = useState('');
    const [eventDate, setEventDate] = useState('');
    const [eventPoster, setEventPoster] = useState(null);
    const [maxPeople, setMaxPeople] = useState(0); // 최대 인원
    const [startTime, setStartTime] = useState(''); // 시작 시간
    const [endTime, setEndTime] = useState(''); // 종료 시간

    const handleFileChange = (e) => {
        setEventPoster(e.target.files[0]);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const formData = new FormData();
        formData.append('request', new Blob([JSON.stringify({
            eventTitle,
            eventContent,
            eventDate,
            maxPeople,  // 최대 인원 추가
            startTime,  // 시작 시간 추가
            endTime,    // 종료 시간 추가
        })], { type: 'application/json' }));

        formData.append('eventPoster', eventPoster);

        try {
            const response = await axios.post('http://13.209.112.29:8080/api/events', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            });
            console.log('Response:', response.data);
        } catch (error) {
            console.error('Error uploading file:', error);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <div>
                <label>Event Title:</label>
                <input
                    type="text"
                    value={eventTitle}
                    onChange={(e) => setEventTitle(e.target.value)}
                    required
                />
            </div>
            <div>
                <label>Event Content:</label>
                <textarea
                    value={eventContent}
                    onChange={(e) => setEventContent(e.target.value)}
                    required
                />
            </div>
            <div>
                <label>Event Date:</label>
                <input
                    type="date"
                    value={eventDate}
                    onChange={(e) => setEventDate(e.target.value)}
                    required
                />
            </div>
            <div>
                <label>Max People:</label>
                <input
                    type="number"
                    value={maxPeople}
                    onChange={(e) => setMaxPeople(e.target.value)}
                    required
                    min="1" // 최소 1명
                />
            </div>
            <div>
                <label>Start Time:</label>
                <input
                    type="time"
                    value={startTime}
                    onChange={(e) => setStartTime(e.target.value)}
                    required
                />
            </div>
            <div>
                <label>End Time:</label>
                <input
                    type="time"
                    value={endTime}
                    onChange={(e) => setEndTime(e.target.value)}
                    required
                />
            </div>
            <div>
                <label>Event Poster:</label>
                <input type="file" onChange={handleFileChange} />
            </div>
            <button type="submit">Upload Event</button>
        </form>
    );
};

export default FileUpload;

