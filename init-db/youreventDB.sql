PGDMP      /                |            youreventDB    17.0    17.0 M    R           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                           false            S           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                           false            T           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                           false            U           1262    16388    youreventDB    DATABASE     �   CREATE DATABASE "youreventDB" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Russian_Russia.1251';
    DROP DATABASE "youreventDB";
                     postgres    false            u           1247    24940    event_status    TYPE     �   CREATE TYPE public.event_status AS ENUM (
    'Planned',
    'Closed',
    'UnderConsideration',
    'Completed',
    'Запланировано',
    'Подтверждено',
    'Рассматривается',
    'Закрыто'
);
    DROP TYPE public.event_status;
       public               postgres    false            l           1247    24754 	   user_role    TYPE     B   CREATE TYPE public.user_role AS ENUM (
    'USER',
    'ADMIN'
);
    DROP TYPE public.user_role;
       public               postgres    false            �            1255    25063 !   agencies_change_verified(boolean) 	   PROCEDURE     �  CREATE PROCEDURE public.agencies_change_verified(IN verified boolean)
    LANGUAGE plpgsql
    AS $$
DECLARE
    ag_cursor CURSOR FOR 
        SELECT agency_id FROM agencies;
    id INT; 
BEGIN
    OPEN ag_cursor; 

    LOOP
        FETCH ag_cursor INTO id;
        EXIT WHEN NOT FOUND; 

        UPDATE agencies
        SET agency_verified = verified
        WHERE agency_id = id;
    END LOOP;

    CLOSE ag_cursor;
END;
$$;
 E   DROP PROCEDURE public.agencies_change_verified(IN verified boolean);
       public               postgres    false            �            1255    25048 /   update_service_price(integer, integer, numeric) 	   PROCEDURE       CREATE PROCEDURE public.update_service_price(IN ag_id integer, IN ser_id integer, IN new_price numeric)
    LANGUAGE sql
    AS $$
    UPDATE agency_services ags
    SET service_price = new_price
    WHERE ags.agency_id = ag_id AND ags.service_id = ser_id;
$$;
 g   DROP PROCEDURE public.update_service_price(IN ag_id integer, IN ser_id integer, IN new_price numeric);
       public               postgres    false            �            1259    16452    agencies    TABLE     �   CREATE TABLE public.agencies (
    agency_id integer NOT NULL,
    agency_name character varying(255) NOT NULL,
    agency_registration_date timestamp(6) without time zone NOT NULL,
    agency_verified boolean,
    agency_contact_info jsonb
);
    DROP TABLE public.agencies;
       public         heap r       postgres    false            �            1259    16451    agencies_ag_id_seq    SEQUENCE     �   CREATE SEQUENCE public.agencies_ag_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.agencies_ag_id_seq;
       public               postgres    false    222            V           0    0    agencies_ag_id_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.agencies_ag_id_seq OWNED BY public.agencies.agency_id;
          public               postgres    false    221            �            1259    24989    agency_services    TABLE     �   CREATE TABLE public.agency_services (
    agency_service_id integer NOT NULL,
    agency_id integer NOT NULL,
    service_id integer NOT NULL,
    service_price numeric(38,2)
);
 #   DROP TABLE public.agency_services;
       public         heap r       postgres    false            �            1259    24988 %   agency_services_agency_service_id_seq    SEQUENCE     �   CREATE SEQUENCE public.agency_services_agency_service_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 <   DROP SEQUENCE public.agency_services_agency_service_id_seq;
       public               postgres    false    230            W           0    0 %   agency_services_agency_service_id_seq    SEQUENCE OWNED BY     o   ALTER SEQUENCE public.agency_services_agency_service_id_seq OWNED BY public.agency_services.agency_service_id;
          public               postgres    false    229            �            1259    24886    services    TABLE     �   CREATE TABLE public.services (
    service_id integer NOT NULL,
    service_name character varying(255) NOT NULL,
    service_type_id integer NOT NULL
);
    DROP TABLE public.services;
       public         heap r       postgres    false            �            1259    25036    agency_services_view    MATERIALIZED VIEW     @  CREATE MATERIALIZED VIEW public.agency_services_view AS
 SELECT ag.agency_id,
    ag.agency_name,
    s.service_id,
    s.service_name
   FROM ((public.agencies ag
     JOIN public.agency_services asg ON ((ag.agency_id = asg.agency_id)))
     JOIN public.services s ON ((asg.service_id = s.service_id)))
  WITH NO DATA;
 4   DROP MATERIALIZED VIEW public.agency_services_view;
       public         heap m       postgres    false    230    228    228    222    222    230            �            1259    16461 
   categories    TABLE     �   CREATE TABLE public.categories (
    cat_id integer NOT NULL,
    cat_name character varying(255) NOT NULL,
    cat_url_photo character varying(255)
);
    DROP TABLE public.categories;
       public         heap r       postgres    false            �            1259    16460    categories_cat_id_seq    SEQUENCE     �   CREATE SEQUENCE public.categories_cat_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public.categories_cat_id_seq;
       public               postgres    false    224            X           0    0    categories_cat_id_seq    SEQUENCE OWNED BY     O   ALTER SEQUENCE public.categories_cat_id_seq OWNED BY public.categories.cat_id;
          public               postgres    false    223            �            1259    25006    event_services    TABLE     �   CREATE TABLE public.event_services (
    event_service_id integer NOT NULL,
    event_id integer NOT NULL,
    agency_service_id integer NOT NULL,
    user_id integer NOT NULL,
    status character varying(50) DEFAULT 'pending'::character varying
);
 "   DROP TABLE public.event_services;
       public         heap r       postgres    false            �            1259    25005 #   event_services_event_service_id_seq    SEQUENCE     �   CREATE SEQUENCE public.event_services_event_service_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 :   DROP SEQUENCE public.event_services_event_service_id_seq;
       public               postgres    false    232            Y           0    0 #   event_services_event_service_id_seq    SEQUENCE OWNED BY     k   ALTER SEQUENCE public.event_services_event_service_id_seq OWNED BY public.event_services.event_service_id;
          public               postgres    false    231            �            1259    16417    events    TABLE     Q  CREATE TABLE public.events (
    event_id integer NOT NULL,
    event_name character varying(255) NOT NULL,
    event_status character varying(255) NOT NULL,
    event_user_id integer NOT NULL,
    event_category_id integer NOT NULL,
    event_created_date timestamp without time zone NOT NULL,
    event_end_date timestamp without time zone NOT NULL,
    event_address character varying(255) NOT NULL,
    event_description character varying(255) NOT NULL,
    event_start_date timestamp without time zone NOT NULL,
    event_people integer,
    event_price integer,
    event_tags text[]
);
    DROP TABLE public.events;
       public         heap r       postgres    false            �            1259    16416    events_event_id_seq    SEQUENCE     �   CREATE SEQUENCE public.events_event_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE public.events_event_id_seq;
       public               postgres    false    218            Z           0    0    events_event_id_seq    SEQUENCE OWNED BY     K   ALTER SEQUENCE public.events_event_id_seq OWNED BY public.events.event_id;
          public               postgres    false    217            �            1259    24877    service_types    TABLE     �   CREATE TABLE public.service_types (
    service_type_id integer NOT NULL,
    service_type_name character varying(255) NOT NULL
);
 !   DROP TABLE public.service_types;
       public         heap r       postgres    false            �            1259    24876 !   service_types_service_type_id_seq    SEQUENCE     �   CREATE SEQUENCE public.service_types_service_type_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 8   DROP SEQUENCE public.service_types_service_type_id_seq;
       public               postgres    false    226            [           0    0 !   service_types_service_type_id_seq    SEQUENCE OWNED BY     g   ALTER SEQUENCE public.service_types_service_type_id_seq OWNED BY public.service_types.service_type_id;
          public               postgres    false    225            �            1259    24885    services_service_id_seq    SEQUENCE     �   CREATE SEQUENCE public.services_service_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 .   DROP SEQUENCE public.services_service_id_seq;
       public               postgres    false    228            \           0    0    services_service_id_seq    SEQUENCE OWNED BY     S   ALTER SEQUENCE public.services_service_id_seq OWNED BY public.services.service_id;
          public               postgres    false    227            �            1259    16426    users    TABLE     �  CREATE TABLE public.users (
    user_id integer NOT NULL,
    user_name character varying(255) NOT NULL,
    user_surname character varying(255) NOT NULL,
    user_registration_date timestamp(6) without time zone NOT NULL,
    user_email character varying(255),
    user_city character varying(255),
    user_password character varying(255) NOT NULL,
    user_role character varying(255),
    user_avatar_url character varying(255)
);
    DROP TABLE public.users;
       public         heap r       postgres    false            �            1259    16425    users_user_id_seq    SEQUENCE     �   CREATE SEQUENCE public.users_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.users_user_id_seq;
       public               postgres    false    220            ]           0    0    users_user_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public.users_user_id_seq OWNED BY public.users.user_id;
          public               postgres    false    219            �           2604    16455    agencies agency_id    DEFAULT     t   ALTER TABLE ONLY public.agencies ALTER COLUMN agency_id SET DEFAULT nextval('public.agencies_ag_id_seq'::regclass);
 A   ALTER TABLE public.agencies ALTER COLUMN agency_id DROP DEFAULT;
       public               postgres    false    222    221    222            �           2604    24992 !   agency_services agency_service_id    DEFAULT     �   ALTER TABLE ONLY public.agency_services ALTER COLUMN agency_service_id SET DEFAULT nextval('public.agency_services_agency_service_id_seq'::regclass);
 P   ALTER TABLE public.agency_services ALTER COLUMN agency_service_id DROP DEFAULT;
       public               postgres    false    229    230    230            �           2604    16464    categories cat_id    DEFAULT     v   ALTER TABLE ONLY public.categories ALTER COLUMN cat_id SET DEFAULT nextval('public.categories_cat_id_seq'::regclass);
 @   ALTER TABLE public.categories ALTER COLUMN cat_id DROP DEFAULT;
       public               postgres    false    223    224    224            �           2604    25009    event_services event_service_id    DEFAULT     �   ALTER TABLE ONLY public.event_services ALTER COLUMN event_service_id SET DEFAULT nextval('public.event_services_event_service_id_seq'::regclass);
 N   ALTER TABLE public.event_services ALTER COLUMN event_service_id DROP DEFAULT;
       public               postgres    false    231    232    232            �           2604    16420    events event_id    DEFAULT     r   ALTER TABLE ONLY public.events ALTER COLUMN event_id SET DEFAULT nextval('public.events_event_id_seq'::regclass);
 >   ALTER TABLE public.events ALTER COLUMN event_id DROP DEFAULT;
       public               postgres    false    217    218    218            �           2604    24880    service_types service_type_id    DEFAULT     �   ALTER TABLE ONLY public.service_types ALTER COLUMN service_type_id SET DEFAULT nextval('public.service_types_service_type_id_seq'::regclass);
 L   ALTER TABLE public.service_types ALTER COLUMN service_type_id DROP DEFAULT;
       public               postgres    false    225    226    226            �           2604    24889    services service_id    DEFAULT     z   ALTER TABLE ONLY public.services ALTER COLUMN service_id SET DEFAULT nextval('public.services_service_id_seq'::regclass);
 B   ALTER TABLE public.services ALTER COLUMN service_id DROP DEFAULT;
       public               postgres    false    228    227    228            �           2604    16429    users user_id    DEFAULT     n   ALTER TABLE ONLY public.users ALTER COLUMN user_id SET DEFAULT nextval('public.users_user_id_seq'::regclass);
 <   ALTER TABLE public.users ALTER COLUMN user_id DROP DEFAULT;
       public               postgres    false    220    219    220            D          0    16452    agencies 
   TABLE DATA           z   COPY public.agencies (agency_id, agency_name, agency_registration_date, agency_verified, agency_contact_info) FROM stdin;
    public               postgres    false    222   �e       L          0    24989    agency_services 
   TABLE DATA           b   COPY public.agency_services (agency_service_id, agency_id, service_id, service_price) FROM stdin;
    public               postgres    false    230   g       F          0    16461 
   categories 
   TABLE DATA           E   COPY public.categories (cat_id, cat_name, cat_url_photo) FROM stdin;
    public               postgres    false    224   Qg       N          0    25006    event_services 
   TABLE DATA           h   COPY public.event_services (event_service_id, event_id, agency_service_id, user_id, status) FROM stdin;
    public               postgres    false    232   ei       @          0    16417    events 
   TABLE DATA           �   COPY public.events (event_id, event_name, event_status, event_user_id, event_category_id, event_created_date, event_end_date, event_address, event_description, event_start_date, event_people, event_price, event_tags) FROM stdin;
    public               postgres    false    218   �i       H          0    24877    service_types 
   TABLE DATA           K   COPY public.service_types (service_type_id, service_type_name) FROM stdin;
    public               postgres    false    226   al       J          0    24886    services 
   TABLE DATA           M   COPY public.services (service_id, service_name, service_type_id) FROM stdin;
    public               postgres    false    228   �l       B          0    16426    users 
   TABLE DATA           �   COPY public.users (user_id, user_name, user_surname, user_registration_date, user_email, user_city, user_password, user_role, user_avatar_url) FROM stdin;
    public               postgres    false    220   /n       ^           0    0    agencies_ag_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.agencies_ag_id_seq', 9, true);
          public               postgres    false    221            _           0    0 %   agency_services_agency_service_id_seq    SEQUENCE SET     S   SELECT pg_catalog.setval('public.agency_services_agency_service_id_seq', 8, true);
          public               postgres    false    229            `           0    0    categories_cat_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.categories_cat_id_seq', 6, true);
          public               postgres    false    223            a           0    0 #   event_services_event_service_id_seq    SEQUENCE SET     R   SELECT pg_catalog.setval('public.event_services_event_service_id_seq', 1, false);
          public               postgres    false    231            b           0    0    events_event_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.events_event_id_seq', 29, true);
          public               postgres    false    217            c           0    0 !   service_types_service_type_id_seq    SEQUENCE SET     P   SELECT pg_catalog.setval('public.service_types_service_type_id_seq', 6, false);
          public               postgres    false    225            d           0    0    services_service_id_seq    SEQUENCE SET     G   SELECT pg_catalog.setval('public.services_service_id_seq', 16, false);
          public               postgres    false    227            e           0    0    users_user_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.users_user_id_seq', 23, true);
          public               postgres    false    219            �           2606    16459    agencies agencies_pkey 
   CONSTRAINT     [   ALTER TABLE ONLY public.agencies
    ADD CONSTRAINT agencies_pkey PRIMARY KEY (agency_id);
 @   ALTER TABLE ONLY public.agencies DROP CONSTRAINT agencies_pkey;
       public                 postgres    false    222            �           2606    24994 $   agency_services agency_services_pkey 
   CONSTRAINT     q   ALTER TABLE ONLY public.agency_services
    ADD CONSTRAINT agency_services_pkey PRIMARY KEY (agency_service_id);
 N   ALTER TABLE ONLY public.agency_services DROP CONSTRAINT agency_services_pkey;
       public                 postgres    false    230            �           2606    16605 "   categories categories_cat_name_key 
   CONSTRAINT     a   ALTER TABLE ONLY public.categories
    ADD CONSTRAINT categories_cat_name_key UNIQUE (cat_name);
 L   ALTER TABLE ONLY public.categories DROP CONSTRAINT categories_cat_name_key;
       public                 postgres    false    224            �           2606    16468    categories categories_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.categories
    ADD CONSTRAINT categories_pkey PRIMARY KEY (cat_id);
 D   ALTER TABLE ONLY public.categories DROP CONSTRAINT categories_pkey;
       public                 postgres    false    224            �           2606    25012 "   event_services event_services_pkey 
   CONSTRAINT     n   ALTER TABLE ONLY public.event_services
    ADD CONSTRAINT event_services_pkey PRIMARY KEY (event_service_id);
 L   ALTER TABLE ONLY public.event_services DROP CONSTRAINT event_services_pkey;
       public                 postgres    false    232            �           2606    16424    events events_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.events
    ADD CONSTRAINT events_pkey PRIMARY KEY (event_id);
 <   ALTER TABLE ONLY public.events DROP CONSTRAINT events_pkey;
       public                 postgres    false    218            �           2606    24884 $   service_types service_types_name_key 
   CONSTRAINT     l   ALTER TABLE ONLY public.service_types
    ADD CONSTRAINT service_types_name_key UNIQUE (service_type_name);
 N   ALTER TABLE ONLY public.service_types DROP CONSTRAINT service_types_name_key;
       public                 postgres    false    226            �           2606    24882     service_types service_types_pkey 
   CONSTRAINT     k   ALTER TABLE ONLY public.service_types
    ADD CONSTRAINT service_types_pkey PRIMARY KEY (service_type_id);
 J   ALTER TABLE ONLY public.service_types DROP CONSTRAINT service_types_pkey;
       public                 postgres    false    226            �           2606    24891    services services_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.services
    ADD CONSTRAINT services_pkey PRIMARY KEY (service_id);
 @   ALTER TABLE ONLY public.services DROP CONSTRAINT services_pkey;
       public                 postgres    false    228            �           2606    16433    users users_pkey 
   CONSTRAINT     S   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public                 postgres    false    220            �           2606    16620    users users_user_email_key 
   CONSTRAINT     [   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_user_email_key UNIQUE (user_email);
 D   ALTER TABLE ONLY public.users DROP CONSTRAINT users_user_email_key;
       public                 postgres    false    220            �           2606    24995 .   agency_services agency_services_agency_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.agency_services
    ADD CONSTRAINT agency_services_agency_id_fkey FOREIGN KEY (agency_id) REFERENCES public.agencies(agency_id) ON DELETE CASCADE;
 X   ALTER TABLE ONLY public.agency_services DROP CONSTRAINT agency_services_agency_id_fkey;
       public               postgres    false    230    222    4758            �           2606    25000 /   agency_services agency_services_service_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.agency_services
    ADD CONSTRAINT agency_services_service_id_fkey FOREIGN KEY (service_id) REFERENCES public.services(service_id) ON DELETE CASCADE;
 Y   ALTER TABLE ONLY public.agency_services DROP CONSTRAINT agency_services_service_id_fkey;
       public               postgres    false    228    4768    230            �           2606    16469    events cat_id_fk1    FK CONSTRAINT     �   ALTER TABLE ONLY public.events
    ADD CONSTRAINT cat_id_fk1 FOREIGN KEY (event_category_id) REFERENCES public.categories(cat_id) ON UPDATE CASCADE ON DELETE CASCADE;
 ;   ALTER TABLE ONLY public.events DROP CONSTRAINT cat_id_fk1;
       public               postgres    false    218    224    4762            �           2606    25018 4   event_services event_services_agency_service_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.event_services
    ADD CONSTRAINT event_services_agency_service_id_fkey FOREIGN KEY (agency_service_id) REFERENCES public.agency_services(agency_service_id) ON DELETE CASCADE;
 ^   ALTER TABLE ONLY public.event_services DROP CONSTRAINT event_services_agency_service_id_fkey;
       public               postgres    false    230    232    4770            �           2606    25013 +   event_services event_services_event_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.event_services
    ADD CONSTRAINT event_services_event_id_fkey FOREIGN KEY (event_id) REFERENCES public.events(event_id) ON DELETE CASCADE;
 U   ALTER TABLE ONLY public.event_services DROP CONSTRAINT event_services_event_id_fkey;
       public               postgres    false    232    218    4752            �           2606    25023 *   event_services event_services_user_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.event_services
    ADD CONSTRAINT event_services_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON DELETE CASCADE;
 T   ALTER TABLE ONLY public.event_services DROP CONSTRAINT event_services_user_id_fkey;
       public               postgres    false    232    4754    220            �           2606    24892 &   services services_service_type_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.services
    ADD CONSTRAINT services_service_type_id_fkey FOREIGN KEY (service_type_id) REFERENCES public.service_types(service_type_id) ON DELETE CASCADE;
 P   ALTER TABLE ONLY public.services DROP CONSTRAINT services_service_type_id_fkey;
       public               postgres    false    228    4766    226            �           2606    16474    events users_fk2    FK CONSTRAINT     �   ALTER TABLE ONLY public.events
    ADD CONSTRAINT users_fk2 FOREIGN KEY (event_user_id) REFERENCES public.users(user_id) ON UPDATE CASCADE ON DELETE CASCADE;
 :   ALTER TABLE ONLY public.events DROP CONSTRAINT users_fk2;
       public               postgres    false    218    4754    220            O           0    25036    agency_services_view    MATERIALIZED VIEW DATA     7   REFRESH MATERIALIZED VIEW public.agency_services_view;
          public               postgres    false    233    4945            D   �   x�ՎOJ�@�דS��)3��?]ՅЍx7c��#�C)D=�@z� E�gxs#gr����}>In֐iz���%Tĥ�JR!��C6J�lN�ΠJ�<�)`��%;��y�+��L�0�/.����Ӵ��s`Ò����O<���o�ә�Ld�;Lt��>��"ͫ�s)�v(r�f�Nw��	~x��}��>Zr�;������sv�w{���\����n��.�N �a�?K@�      L   =   x�Eʹ 0���������� D���I)�|���?I�J�]����?7��AG�f~�m�      F     x����nT1��OA�w�3�H<�f��$�j�)	(U$*D��F�<���D���<���կ����>����n���O�go�q�<_,x��7��u\�|���c��j3����L.����3:e�y{����b<��n�y�_)�% sI�)Ȱ)JI����o��x<�PY�gc��N��|a�-� �A�A��	�k����~yT���x�w�v7���=�]UN�)#Y�< �H�d!��� 6Z�\��R�����FoJRԙY�^֛z;]���0�#'�C�EnN�,�Kv���:�޳ �( �����6Jbђ;�������0�r��8�nXdIr@�.Hk`�~fc��ъ N	�UJN�s��~�7��t��/����q�8H1��!�r��x]\ ��2��k����J$mZVA�9A�9�F60c��-���W��@5��x� ��ޠUQ�H�1�1"��T>�[����-��B�]`L�{=����h      N      x������ � �      @   �  x�ݔ�nA���S����3�;$R !��2M��$�AH�Cb�X �@
�	Q_l����+̾���|8�ɾ�ٝٙ��眢c*i){���^���Ot�}���3�e�P�K�U��qqdM䜶I��*��l��ky��wq��^D'8��G�i��u���e4����4�Tb)��>�^�Ax�;֦�r��&؜�.b�dǊ2�UR62V;_%+���#�%����t�c�~��9{������@�F6drx��9)M�z��=D�%��B�%���P|~��L��*Y2�ڬ_cf#g�}P���.CqO>���u+�R*��gL7�%ܕ6r)�V"湤��(!ߒ΁T�]&��CD�2�ټE�@K�P�?��Ք=({�~�����:����u�Ȼ_5�u��Qu��Dkk+	.dim��_�b���/��#Ĥ�y��n�M^��],��V5dL�`ě3����R�	N��(ͧI5cJ�e����bs��ʐ�|��p��`��"hdׅ,X�"8�Ь_"M�iޜt���@z,�8���\BR.�)y`D�A�"s�(�2-�%� VF��N�Y����K�1S,�;hS��.�\�te�J���vx�w�{{�\]Sm�|�bW=}���x����K���[޶�n�+�X��N����%W-�ti�f������w������g��_ڽ������Q�ouvn��A(�l�k��o��      H   l   x�M���@�b,��1�d�N#D�-�u��#G+��N&��|���`wͅ��լ�x52�yF-��$�|>Η5z:�f�V�=\�=�L�D&�?QuQ�D�P      J   B  x�}�=N1���)|�8-JAKA��H�BHD�$��l��l�@h�ڙ�o�̎]W�zGǇ=��6��Oΰm��s�P�i
��Q�	��RK0�3JQ�)(,?k�Z���'(	EDV�w�:H&k,�i:��X^�~�<��+�`�����d�*�]�fXӢ���-xd�s���[,x^�FXJ��n6��=�,�{�2�翋:&��ˏ�4��*[�ƌ`�bq��n�&����l�#�L���t
��p�`�rœ4��f���$�şS#H�8�w�ue�d����k�g��JGT���1�'�<�      B   G  x�Ֆ[o�F���_���w؞�	#�Hs�B�7c<�O�D�MU�R+mWj��]�p�WU�6�4��o�Q�l6�����
f ��3��~�#yM�矑s���1���ӅKrA ��s��|a�dLN�"n��5�a����sr� (0G;�� ����q};��p�!�0k���`���].�9���I�S��<
�����_�\1E1
���>EN��Ŧ�>����H*/I�(�$�G9Q�{9� e�'�
��Mq2�rKo�5e����+�C�������RD��@7-�Nv-+�(1�[J�'E���~!��3��Ct�C�L�l"C�ˊs���C�g�J���ț�lnV�R�M�f��ծN�(O�h����Ѷ	�:�ߡ�)�6�)r?޵ E������+m�W�o��$7t�!)���:�2RQa�<dlw�\���Оe�K�u��ohJ����
_6��Ƙ뤺�������I�(,��T���jW�F=4v��y�e��\Ϳ����k��F;�&˫%� ,�"��*���Q��gǭ�fk���(�1����d�4�H/k6�=�ڒ�ݮZ+��L��Ί�����r�H���~IA�������/��~,�ˌ�Ґ^�=��l@U�M?��-uU7�RLoZk+%ȏ���ÄSꭓ�kz����1����~��~GwW̐̕�iD^-r�<+�+�l�t��2�`i�>��/��2�/r�(��"��Ge�T�sw����:��]h�{��=�x���z�&��`�j}��K�Șv��d�d)���nt�)�bp�XL��EB=��T��,�w���&���O���;WV7VB��]��ݘ8�z�%E0����.�m?_Zޞ��V�Lo�<�I�9�� ,"[�qt�f�K ���;�C�\�J�<
 2�Jc�TJ�b}R:F���!�¡�7��N S�]1�y�B{�^I>��YY�2q�DKer�ꣃ�@,���(L�]����mzQgR��hO���9��������/�{=@XJ҄�Y�W�1���Ӹ&y�*�¢�6�Nzln�[��֠��i��AY�t?i����%�>;X-OOٵ���V���     